package com.example.ordering_lecture.review.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.item.controller.MemberServiceClient;
import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.repository.ItemRepository;
import com.example.ordering_lecture.review.controller.OrderServiceClient;
import com.example.ordering_lecture.review.dto.ReviewRequestDto;
import com.example.ordering_lecture.review.dto.ReviewResponseDto;
import com.example.ordering_lecture.review.dto.ReviewUpdateDto;
import com.example.ordering_lecture.review.entity.Review;
import com.example.ordering_lecture.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final AmazonS3Client amazonS3Client;
    private final OrderServiceClient orderServiceClient;
    private final MemberServiceClient memberServiceClient;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ReviewService(ReviewRepository reviewRepository, ItemRepository itemRepository, AmazonS3Client amazonS3Client, OrderServiceClient orderServiceClient, MemberServiceClient memberServiceClient) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
        this.amazonS3Client = amazonS3Client;
        this.orderServiceClient = orderServiceClient;
        this.memberServiceClient = memberServiceClient;
    }

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto)throws OrTopiaException {
        Item item = itemRepository.findById(reviewRequestDto.getItemId()).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_ITEM)
        );
        String fileName = reviewRequestDto.getBuyerEmail() + System.currentTimeMillis();
        String fileUrl = null;
        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(reviewRequestDto.getImagePath().getContentType());
            metadata.setContentLength(reviewRequestDto.getImagePath().getSize());
            amazonS3Client.putObject(bucket, fileName, reviewRequestDto.getImagePath().getInputStream(), metadata);
            fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new OrTopiaException(ErrorCode.S3_SERVER_ERROR);
        }
        Review review = reviewRequestDto.toEntity(item,fileUrl);
        reviewRepository.save(review);
        // 리뷰 작성시 Item 리뷰 수 , 총점수 업데이트.
        item.updateScore(reviewRequestDto.getScore());
        //TODO : 추후 카프카로 변경. 주문디테일 테이블에 리뷰 작성 표시.
        orderServiceClient.checkReview(reviewRequestDto.getOrderDetailId());
        return ReviewResponseDto.toDto(review,null);
    }

    public List<ReviewResponseDto> showAllReview()throws OrTopiaException{
        List<Review> reviews = reviewRepository.findAll();
        if(reviews.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_REVIEWS);
        }
        return reviews.stream()
                .map(review -> {
                    String name = memberServiceClient.searchNameByEmail(review.getBuyerEmail());
                    return ReviewResponseDto.toDto(review,name);
                })
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> showItemReviews(Long itemId)throws OrTopiaException {
        List<Review> reviews = reviewRepository.findAllByItemId(itemId);
        if(reviews.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_REVIEWS);
        }
        return reviews.stream()
                .map(review -> {
                  String name = memberServiceClient.searchNameByEmail(review.getBuyerEmail());
                  return ReviewResponseDto.toDto(review,name);
                })
                .collect(Collectors.toList());
    }
    public List<ReviewResponseDto> showBuyerReviews(String email)throws OrTopiaException {
        List<Review> reviews = reviewRepository.findAllByBuyerEmail(email);
        if(reviews.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_REVIEWS);
        }
        return reviews.stream()
                .map(review -> {
                    String name = memberServiceClient.searchNameByEmail(review.getBuyerEmail());
                    return ReviewResponseDto.toDto(review,name);
                })
                .collect(Collectors.toList());
    }
    public void deleteReview(Long id) throws OrTopiaException{
        try {
            reviewRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new OrTopiaException(ErrorCode.NOT_FOUND_REVIEW);
        }
    }

    @Transactional
    public ReviewResponseDto updateReview(ReviewUpdateDto reviewUpdateDto)throws OrTopiaException {
        Review review = reviewRepository.findById(reviewUpdateDto.getId()).orElseThrow();
        reviewUpdateDto.toUpdate(review);
        String name = memberServiceClient.searchNameByEmail(review.getBuyerEmail());
        return ReviewResponseDto.toDto(review,name);
    }
}

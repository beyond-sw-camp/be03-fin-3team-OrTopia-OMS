package com.example.ordering_lecture.review.service;

import com.example.ordering_lecture.common.ErrorCode;
import com.example.ordering_lecture.common.OrTopiaException;
import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.repository.ItemRepository;
import com.example.ordering_lecture.review.dto.ReviewRequestDto;
import com.example.ordering_lecture.review.dto.ReviewResponseDto;
import com.example.ordering_lecture.review.dto.ReviewUpdateDto;
import com.example.ordering_lecture.review.entity.Review;
import com.example.ordering_lecture.review.repository.ReviewRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    public ReviewService(ReviewRepository reviewRepository, ItemRepository itemRepository) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
    }

    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto)throws OrTopiaException {
        Item item = itemRepository.findById(reviewRequestDto.getItemId()).orElseThrow(
                ()-> new OrTopiaException(ErrorCode.NOT_FOUND_ITEM)
        );
        Review review = reviewRequestDto.toEntity(item);
        reviewRepository.save(review);
        return ReviewResponseDto.toDto(review);
    }

    public List<ReviewResponseDto> showAllReview()throws OrTopiaException{
        List<Review> reviews = reviewRepository.findAll();
        if(reviews.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_REVIEWS);
        }
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> showItemReviews(Long itemId)throws OrTopiaException {
        List<Review> reviews = reviewRepository.findAllByItemId(itemId);
        if(reviews.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_REVIEWS);
        }
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public List<ReviewResponseDto> showBuyerReviews(Long buyerId)throws OrTopiaException {
        List<Review> reviews = reviewRepository.findAllByBuyerId(buyerId);
        if(reviews.isEmpty()){
            throw new OrTopiaException(ErrorCode.EMPTY_REVIEWS);
        }
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
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
        return ReviewResponseDto.toDto(review);
    }
}

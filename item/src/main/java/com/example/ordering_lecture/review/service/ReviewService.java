package com.example.ordering_lecture.review.service;

import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.item.repository.ItemRepository;
import com.example.ordering_lecture.review.dto.ReviewRequestDto;
import com.example.ordering_lecture.review.dto.ReviewResponseDto;
import com.example.ordering_lecture.review.dto.ReviewUpdateDto;
import com.example.ordering_lecture.review.entity.Review;
import com.example.ordering_lecture.review.repository.ReviewRepository;
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

    public Object createReview(ReviewRequestDto reviewRequestDto) {
        //TODO : 에러 코드 추후 수정
        Item item = itemRepository.findById(reviewRequestDto.getItemId()).orElseThrow();
        Review review = reviewRequestDto.toEntity(item);
        reviewRepository.save(review);
        return ReviewResponseDto.toDto(review);
    }

    public Object showAllReview() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public Object showItemReviews(Long itemId) {
        List<Review> reviews = reviewRepository.findAllByItemId(itemId);
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public Object showBuyerReviews(String email) {
        List<Review> reviews = reviewRepository.findAllByBuyerEmail(email);
        return reviews.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewRepository.findById(id).orElseThrow();
        reviewUpdateDto.toUpdate(review);
        return ReviewResponseDto.toDto(review);
    }
}

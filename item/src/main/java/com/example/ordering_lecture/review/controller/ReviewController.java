package com.example.ordering_lecture.review.controller;

import com.example.ordering_lecture.review.dto.ReviewRequestDto;
import com.example.ordering_lecture.review.dto.ReviewUpdateDto;
import com.example.ordering_lecture.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review_server")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public Object createReview(@RequestBody ReviewRequestDto reviewRequestDto){
        reviewService.createReview(reviewRequestDto);
        return null;
    }
    // 모든 리뷰 조회
    @GetMapping("/show_all")
    public Object showAllReview(){
        reviewService.showAllReview();
        return null;
    }
    // 특정 아이템의 id로 리뷰 조회
    @GetMapping("/show_item/{id}")
    public Object showItemReviews(@PathVariable Long itemId){
        reviewService.showItemReviews(itemId);
        return null;
    }
    // 특정 사용자가 작성한 리뷰 조회
    @GetMapping("/show_buyer/{email}")
    public Object showBuyerReviews(@PathVariable String email){
        reviewService.showBuyerReviews(email);
        return null;
    }
    // 리뷰 id로 특정 리뷰 업데이트
    @PatchMapping("/update/{id}")
    public Object updateReviews(@PathVariable Long id, @RequestBody ReviewUpdateDto reviewUpdateDto){
        reviewService.updateReview(id,reviewUpdateDto);
        return null;
    }

    // 리뷰의 id로 특정 리뷰를 삭제
    @DeleteMapping("/delete/{id}")
    public Object deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return null;
    }

}

package com.example.ordering_lecture.review.controller;

import com.example.ordering_lecture.common.OrTopiaResponse;
import com.example.ordering_lecture.review.dto.ReviewRequestDto;
import com.example.ordering_lecture.review.dto.ReviewResponseDto;
import com.example.ordering_lecture.review.dto.ReviewUpdateDto;
import com.example.ordering_lecture.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrTopiaResponse> createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.createReview(reviewRequestDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("create success",reviewResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.CREATED);
    }
    // 모든 리뷰 조회
    @GetMapping("/show_all")
    public ResponseEntity<OrTopiaResponse> showAllReview(){
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.showAllReview();
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",reviewResponseDtoList);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 특정 아이템의 id로 리뷰 조회
    @GetMapping("/show_item/{itemId}")
    public ResponseEntity<OrTopiaResponse> showItemReviews(@PathVariable Long itemId){
        List<ReviewResponseDto> reviewResponseDtoList =  reviewService.showItemReviews(itemId);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",reviewResponseDtoList);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 특정 사용자가 작성한 리뷰 조회
    @GetMapping("/show_buyer/{buyerId}")
    public ResponseEntity<OrTopiaResponse> showBuyerReviews(@PathVariable Long buyerId){
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.showBuyerReviews(buyerId);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("read success",reviewResponseDtoList);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }
    // 리뷰 id로 특정 리뷰 업데이트
    @PatchMapping("/update")
    public ResponseEntity<OrTopiaResponse> updateReviews(@Valid @RequestBody ReviewUpdateDto reviewUpdateDto){
        ReviewResponseDto reviewResponseDto = reviewService.updateReview(reviewUpdateDto);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("update success",reviewResponseDto);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }

    // 리뷰의 id로 특정 리뷰를 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrTopiaResponse> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        OrTopiaResponse orTopiaResponse = new OrTopiaResponse("delete success",null);
        return new ResponseEntity<>(orTopiaResponse, HttpStatus.OK);
    }

}

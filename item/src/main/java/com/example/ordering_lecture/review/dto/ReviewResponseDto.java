package com.example.ordering_lecture.review.dto;

import com.example.ordering_lecture.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long id;
    private byte score;
    private String content;
    private Long buyerId;
    private Long itemId;

    public static ReviewResponseDto toDto(Review review){
        return ReviewResponseDto.builder()
                .buyerId(review.getBuyerId())
                .id(review.getId())
                .itemId(review.getItem().getId())
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }
}

package com.example.ordering_lecture.review.dto;

import com.example.ordering_lecture.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long id;
    private byte score;
    private String content;
    private String buyerEmail;
    private String name;
    private Long itemId;
    private String imagePath;
    private String date;

    public static ReviewResponseDto toDto(Review review,String name){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = review.getCreatedTime().format(formatter);
        return ReviewResponseDto.builder()
                .name(name)
                .date(formattedDateTime)
                .buyerEmail(review.getBuyerEmail())
                .id(review.getId())
                .itemId(review.getItem().getId())
                .score(review.getScore())
                .content(review.getContent())
                .imagePath(review.getImagePath())
                .build();
    }
}

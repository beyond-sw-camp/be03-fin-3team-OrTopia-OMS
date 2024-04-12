package com.example.ordering_lecture.review.dto;

import com.example.ordering_lecture.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateDto {
    private Long id;
    private Byte score;
    private String content;

    public Review toUpdate(Review review){
        if(score != null){
            review.updateScore(score);
        }
        if(content != null){
            review.updateContent(content);
        }
        return review;
    }
}

package com.example.ordering_lecture.review.dto;

import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private byte score;
    private String content;
    private String buyerEmail;
    private Long itemId;

    public Review toEntity(Item item){
        return Review.builder()
                .score(this.score)
                .content(this.content)
                .buyerEmail(this.buyerEmail)
                .item(item)
                .build();
    }
}

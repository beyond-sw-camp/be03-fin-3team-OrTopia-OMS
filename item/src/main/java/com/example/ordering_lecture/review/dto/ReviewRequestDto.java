package com.example.ordering_lecture.review.dto;

import com.example.ordering_lecture.item.entity.Item;
import com.example.ordering_lecture.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "EMPTY_REVIEW_SCORE")
    private byte score;
    @NotNull(message = "EMPTY_REVIEW_CONTENT")
    private String content;
    @NotNull(message = "EMPTY_REVIEW_BUYER_ID")
    private Long buyerId;
    @NotNull(message = "EMPTY_REVIEW_ITEM_ID")
    private Long itemId;

    public Review toEntity(Item item){
        return Review.builder()
                .score(this.score)
                .content(this.content)
                .buyerId(this.buyerId)
                .item(item)
                .build();
    }
}

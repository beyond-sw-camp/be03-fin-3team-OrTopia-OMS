package com.example.ordering_lecture.item.dto;

import com.example.ordering_lecture.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemResponseDto {
    private Long id;
    private String name;
    private int stock;
    private int price;
    private String category;
    private String detail;
    private String imagePath;
    private int minimumStock;
    private boolean delYN;
    private boolean isBaned;
    private Long sellerId;
    private List<ItemOptionResponseDto> itemOptionResponseDtoList;
    private String createdTime;
    private Long reviewNumber;
    private Long score;


    public static ItemResponseDto toDto(Item item){
        return ItemResponseDto.builder()
                .reviewNumber(item.getReviewNumber())
                .score(item.getScore())
                .name(item.getName())
                .imagePath(item.getImagePath())
                .price(item.getPrice())
                .detail(item.getDetail())
                .minimumStock(item.getMinimumStock())
                .sellerId(item.getSellerId())
                .id(item.getId())
                .category(item.getCategory().toString())
                .delYN(item.isDelYN())
                .isBaned(item.isBaned())
                .stock(item.getStock())
                .createdTime(item.getCreatedTime().toString())
                .itemOptionResponseDtoList(new ArrayList<>())
                .build();
    }
}

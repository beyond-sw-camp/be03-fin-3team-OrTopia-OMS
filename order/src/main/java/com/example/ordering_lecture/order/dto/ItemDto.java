package com.example.ordering_lecture.order.dto;

import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private String imagePath;
}

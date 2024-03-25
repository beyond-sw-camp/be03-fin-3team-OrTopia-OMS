package com.example.ordering_lecture.item.dto;

import lombok.Data;

@Data
public class ItemQuantityUpdateDto {
    private Long id;
    private int stockQuantity;
}

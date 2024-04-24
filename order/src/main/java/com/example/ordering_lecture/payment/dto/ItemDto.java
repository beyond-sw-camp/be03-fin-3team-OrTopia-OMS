package com.example.ordering_lecture.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private int count;
    private List<ItemOptionDto> options;
}

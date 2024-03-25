package com.example.ordering_lecture.item.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemReqDto {
    private String name;
    private String category;
    private int price;
    private int stockQuantity;
    private MultipartFile itemImage;
}

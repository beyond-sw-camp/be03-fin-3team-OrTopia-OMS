package com.example.ordering_lecture.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SellerGraphPriceData {
    private Date createdTime;
    private Long price;
}
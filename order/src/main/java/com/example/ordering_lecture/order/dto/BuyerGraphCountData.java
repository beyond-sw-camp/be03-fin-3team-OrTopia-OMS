package com.example.ordering_lecture.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BuyerGraphCountData {
    private Date createdTime;
    private Long count;
}

package com.example.ordering_lecture.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInfoDto {
    private int price;
    private List<ItemDto> itemDtoList;
}

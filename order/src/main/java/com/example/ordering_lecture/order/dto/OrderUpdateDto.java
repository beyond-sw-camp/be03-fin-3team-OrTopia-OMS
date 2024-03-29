package com.example.ordering_lecture.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateDto {
    @NotNull
    private Long id;
    @NotNull
    private String statue;
}

package com.example.ordering_lecture.payment.dto;

import lombok.Getter;

@Getter
public class Amount {
    private int total; // 총 결제 금액
    private int tax_free; // 비과세 금액
    private int tax; // 부가세 금액
}
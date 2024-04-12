package com.example.ordering_lecture.payment.dto;

import lombok.Getter;

@Getter
public class PayRequestDto {
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지
    private String created_at;
}

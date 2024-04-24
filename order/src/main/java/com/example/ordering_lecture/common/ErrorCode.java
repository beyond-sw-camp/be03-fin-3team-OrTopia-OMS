package com.example.ordering_lecture.common;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public enum ErrorCode {
    REDIS_ERROR("O1","redis 저장에 실패했습니다."),
    ACCESS_DENIED("O2","잘못된 접근입니다."),
    ITEM_QUANTITY_ERROR("O3","아이템의 재고가 부족합니다." ),
    NOT_FOUND_ORDERDETAIL("O4","해당 상세 주문이 없습니다.");
    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

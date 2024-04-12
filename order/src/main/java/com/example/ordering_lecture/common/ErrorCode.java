package com.example.ordering_lecture.common;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public enum ErrorCode {
    WRONG_ITEM_INFORMATION("I1","아이템의 정보를 다시 입력해주세요."),
    NOT_FOUND_ITEM("I2","아이템을 찾을 수 없습니다"),
    EMPTY_ITEMS("I3","아이템 목록을 찾을 수 없습니다"),
    EMPTY_ITEM_NAME("I4","아이템의 이름을 입력해주세요"),
    EMPTY_ITEM_STOCK("I5","아이템의 재고를 입력해주세요"),
    EMPTY_ITEM_PRICE("I6","아이템의 가격을 입력해주세요"),
    EMPTY_ITEM_CATEGORY("I7","아이템의 카테고리를 입력해주세요"),
    EMPTY_ITEM_DETAIL("I8","아이템의 설명을 입력해주세요"),
    EMPTY_ITEM_SELLER("I9","아이템의 판매자 이메일 입력해주세요"),
    EMPTY_REVIEW_SCORE("I10","리뷰 점수를 입력해주세요"),
    EMPTY_REVIEW_CONTENT("I1","리뷰 내용을 입력해 주세요"),
    EMPTY_REVIEW_BUYER_ID("I12","잘못된 구매자 입니다."),
    EMPTY_REVIEW_ITEM_ID("I13","잘못된 아이템 입니다."),
    EMPTY_REVIEWS("I14","리뷰목록을 찾을 수 없습니다" ),
    NOT_FOUND_REVIEW("I15","해당 리뷰를 찾을 수 없습니다.");

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

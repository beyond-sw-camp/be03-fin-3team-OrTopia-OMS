package com.example.ordering_lecture.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMPTY_NOTICE_TITLE("N1", "공지사항의 제목을 입력해주세요."),
    EMPTY_NOTICE_CONTENTS("N2", "공지사항의 내용을 입력해주세요."),
    WRONG_NOTICE_DATE("N3", "잘못된 날짜 형식입니다."),
    NOT_FOUND_NOTICE("N4", "해당 공지사항을 찾을 수 없습니다."),
    EMPTY_NOTICE_START_DATE("N5", "공지 시작일을 입력해주세요."),
    EMPTY_NOTICE_END_DATE("N6", "공지 종료일을 입력해주세요."),
    PAST_NOTICE_START_DATE("N7", "시작일은 오늘 이후의 날짜로 설정해야 합니다."),
    END_DATE_BEFORE_START_DATE("N8", "종료일은 시작일 이후의 날짜로 설정해야 합니다."),
    EMPTY_NOTICE_ID("N9", "공지사항 ID를 입력해주세요."),
    DELETED_NOTICE("N10", "이미 삭제된 공지사항입니다.");

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

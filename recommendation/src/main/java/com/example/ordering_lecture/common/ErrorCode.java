package com.example.ordering_lecture.common;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public enum ErrorCode {
    NOT_FOUND_FILE("R1","해당 파일을 찾을 수 없습니다."),
    NOT_MAKE_SIMILARITY("R2","유사도를 구할 수 없습니다."),
    NOT_MAKE_TASTE("R3","추천 아이템을 구할 수 없습니다."),
    NOT_SET_URL("R4","DB 경로를 설정할 수 없습니다."),
    NOT_SET_USER("R5","DB 유저 정보를 설정할 수 없습니다."),
    NOT_SET_PASSWORD("R6","DB 비밀번호 정보를 설정할 수 없습니다."),
    REDIS_ERROR("R7","레디스 저장에 실패했습니다"),
    JSON_PARSE_ERROR("R8","JSON 형변환에 실패했습니다"),
    DB_ERROR("R9","buyer ID를 불러 오는데 실패했습니다"),
    REDIS_NOT_FOUND_KEY("R10","키 값이 존재하지 않습니다");


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

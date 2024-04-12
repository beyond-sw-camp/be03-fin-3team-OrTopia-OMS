package com.example.ordering_lecture.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_IS_ESSENTIAL("M1","이메일을 입력해주세요"),
    EMAIL_IS_NOT_VALID("M2","이메일 형식이 아닙니다"),
    NAME_IS_ESSENTIAL("M3","이름을 입력해주세요"),
    PASSWORD_IS_ESSENTIAL("M4","비밀번호를 입력해주세요"),
    PASSWORD_LENGTH("M5","비밀번호를 4자리 이상 입력해주세요"),
    AGE_IS_ESSENTIAL("M6","나이를 입력해주세요"),
    GENDER_IS_ESSENTIAL("M7","성별을 선택해주세요"),
    PHONENUMBER_IS_ESSENTIAL("M8","전화번호를 입력해주세요"),
    NOT_FOUND_MEMBER("M9","해당 회원이 없습니다"),
    SELLERID_IS_ESSENTIAL("M10","판매자 ID를 입력해주세요"),
    BUYER_IS_ESSENTIAL("M11","구매자 ID를 입력해주세요"),
    NOT_FOUND_MEMBERS("M12","조건에 맞는 회원목록이 없습니다"),
    NOT_FOUND_SELLER("M13","해당 판매자가 없습니다"),
    NOT_FOUND_SELLERS("M14","조건에 맞는 판매자 목록이 없습니다"),
    BUSINNESSNUMBER_IS_ESSENTIAL("M15","사업자 번호를 입력해주세요"),
    COMPANYNAME_IS_ESSENTIAL("M16","회사이름을 입력해주세요"),
    BUSINNESSTYPE_IS_ESSENTIAL("M17","업종을 입력해주세요"),
    STARTTIME_IS_ESSENTIAL("M18","벤 시작시간을 입력해주세요"),
    ENDTIME_IS_ESSENTIAL("M19","벤 끝낼시간을 입력해주세요"),
    NOT_FOUND_BANED_SELLER("M20","해당 벤 판매자가 없습니다"),
    NOT_FOUND_BANED_SELLERS("M21","조건에 맞는 벤 판매자가 없습니다"),
    NOT_FOUND_ADDRESS("A1", "해당 주소가 없습니다."),
    NOT_FOUND_ADDRESS_BY_EMAIL("A2", "해당 이메일로 등록된 주소가 없습니다."),
    ADDRESS_IS_ESSENTIAL("A3", "주소 정보를 입력해주세요");

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

package com.example.ordering_lecture.common;

import lombok.Data;

@Data
public class MemberLoginResDto {
    private Long id;
    private String accessToken;
    private String refreshToken;

    public MemberLoginResDto(Long id, String accessToken, String refreshToken ) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }

}

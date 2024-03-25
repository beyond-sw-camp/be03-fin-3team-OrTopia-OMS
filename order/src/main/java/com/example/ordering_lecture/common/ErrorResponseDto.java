package com.example.ordering_lecture.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseDto {
    public static ResponseEntity<Map<String,Object>> makeMessage(HttpStatus status, String message){
        Map<String,Object> body = new HashMap<>();
        body.put("status",String.valueOf(status.value()));
        body.put("error_message",message);
        //본문 // 헤더
        return new ResponseEntity<>(body,status);
    }
}

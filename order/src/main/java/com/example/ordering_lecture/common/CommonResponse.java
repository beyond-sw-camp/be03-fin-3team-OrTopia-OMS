package com.example.ordering_lecture.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object result;
}

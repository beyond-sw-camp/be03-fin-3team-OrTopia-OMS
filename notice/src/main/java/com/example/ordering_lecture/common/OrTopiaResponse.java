package com.example.ordering_lecture.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrTopiaResponse {
    private String message;
    private Object result;

    public OrTopiaResponse(String message) {
        this.message = message;
    }
}

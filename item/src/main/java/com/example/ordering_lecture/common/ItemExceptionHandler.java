package com.example.ordering_lecture.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ItemExceptionHandler {
    @ExceptionHandler(OrTopiaException.class)
    public ResponseEntity<Map<String,String>> OrTopiaException(OrTopiaException e){
        Map<String,String> body = new HashMap<>();
        body.put("message",e.getMessage());
        body.put("code",e.getCode());
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String,String>> BindException(BindException e){
        Map<String,String> body = new HashMap<>();
        ErrorCode errorCode = ErrorCode.valueOf(e.getBindingResult().getFieldError().getDefaultMessage());
        OrTopiaException orTopiaException = new OrTopiaException(errorCode);
        body.put("message",orTopiaException.getMessage());
        body.put("code",orTopiaException.getCode());
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

}

package com.example.ordering_lecture.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerClass {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,Object>> entityNotFoundHandler(EntityNotFoundException e){
        log.error("Handler EntityNotFundException message : " + e.getMessage());
        return ErrorResponseDto.makeMessage(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> entityNotFoundHandler(IllegalArgumentException e){
        log.error("Handler IllegalArgumentException message : " + e.getMessage());
        return ErrorResponseDto.makeMessage(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidException(MethodArgumentNotValidException e){
        return ErrorResponseDto.makeMessage(HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}

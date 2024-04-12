package com.example.ordering_lecture.common;

public class OrTopiaException extends RuntimeException{
    private ErrorCode errorCode;

    public OrTopiaException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getMessage(){
        return this.errorCode.getMessage();
    }
    public String getCode(){
        return this.errorCode.getCode();
    }
}

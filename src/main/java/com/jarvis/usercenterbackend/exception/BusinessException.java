package com.jarvis.usercenterbackend.exception;

import com.jarvis.usercenterbackend.common.ErrorCode;

public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        //super message
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorcode) {
        super(errorcode.getMessage());
        this.code = errorcode.getCode();
        this.description = errorcode.getDescription();
    }

    public BusinessException(ErrorCode errorcode, String description) {
        super(errorcode.getMessage());
        this.code = errorcode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

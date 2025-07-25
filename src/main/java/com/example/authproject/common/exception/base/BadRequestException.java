package com.example.authproject.common.exception.base;

import com.example.authproject.common.exception.code.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
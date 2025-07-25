package com.example.authproject.common.exception.base;

import com.example.authproject.common.exception.code.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

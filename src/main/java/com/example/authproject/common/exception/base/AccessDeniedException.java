package com.example.authproject.common.exception.base;

import com.example.authproject.common.exception.base.CustomException;
import com.example.authproject.common.exception.code.ErrorCode;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
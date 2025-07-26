package com.example.authproject.common.exception.base;

import com.example.authproject.common.exception.base.CustomException;
import com.example.authproject.common.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
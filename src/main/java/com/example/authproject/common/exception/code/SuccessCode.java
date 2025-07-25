package com.example.authproject.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // 201 Created
    CREATED(HttpStatus.CREATED, "요청이 성공적으로 처리되어 리소스가 생성되었습니다."),

    // 200 OK
    OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
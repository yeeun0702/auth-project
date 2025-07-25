package com.example.authproject.controller;

import com.example.authproject.common.exception.base.CustomException;
import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.common.exception.code.SuccessCode;
import com.example.authproject.common.response.ApiResponseDto;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/ping")
    public ApiResponseDto<String> ping() {
        return ApiResponseDto.success(SuccessCode.OK, "pong");
    }

    // 성공 테스트
    @GetMapping("/success")
    public ApiResponseDto<String> success() {
        return ApiResponseDto.success(SuccessCode.OK, null);
    }

    // 실패 테스트
    @GetMapping("/error")
    public void error() {
        throw new CustomException(ErrorCode.TEST_ERROR);
    }

}


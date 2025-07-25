package com.example.authproject.controller;

import com.example.authproject.common.exception.code.SuccessCode;
import com.example.authproject.common.response.ApiResponseDto;
import com.example.authproject.dto.request.LoginRequest;
import com.example.authproject.dto.request.SignupRequest;
import com.example.authproject.dto.response.LoginResponse;
import com.example.authproject.dto.response.SignupResponse;
import com.example.authproject.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;

    @PostMapping("/signup")
    public ApiResponseDto<SignupResponse> signup(@Valid @RequestBody final SignupRequest request) {
        return ApiResponseDto.success(SuccessCode.CREATED, authService.signup(request));
    }

    @PostMapping("/login")
    public ApiResponseDto<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponseDto.success(SuccessCode.CREATED, authService.login(request));
    }

}

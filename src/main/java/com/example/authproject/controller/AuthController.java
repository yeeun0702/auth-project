package com.example.authproject.controller;

import com.example.authproject.common.exception.code.SuccessCode;
import com.example.authproject.common.response.ApiResponseDto;
import com.example.authproject.dto.request.LoginRequest;
import com.example.authproject.dto.request.SignupRequest;
import com.example.authproject.dto.response.LoginResponse;
import com.example.authproject.dto.response.SignupResponse;
import com.example.authproject.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원가입 및 로그인 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(
        summary = "회원가입",
        description = "새로운 사용자를 등록합니다.",
        responses = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                content = @Content(schema = @Schema(implementation = SignupResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 유효성 실패"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 사용자")
        }
    )
    public ApiResponseDto<SignupResponse> signup(@Valid @RequestBody final SignupRequest request) {
        return ApiResponseDto.success(SuccessCode.CREATED, authService.signup(request));
    }

    @PostMapping("/login")
    @Operation(
        summary = "로그인",
        description = "사용자 정보를 검증하고 JWT 토큰을 반환합니다.",
        responses = {
            @ApiResponse(responseCode = "201", description = "로그인 성공",
                content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 사용자 정보")
        }
    )
    public ApiResponseDto<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponseDto.success(SuccessCode.CREATED, authService.login(request));
    }
}

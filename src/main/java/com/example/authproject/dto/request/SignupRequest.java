package com.example.authproject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청 DTO")
public record SignupRequest(

    @Schema(
        description = "사용자 ID (최대 10자)",
        example = "testUser",
        minLength = 1,
        maxLength = 10
    )
    @NotBlank(message = "유저 이름은 필수입니다.")
    @Size(min = 1, max = 10, message = "최대 10글자까지 가능합니다.")
    String username,

    @Schema(
        description = "사용자 닉네임 (최대 15자)",
        example = "홍길동",
        maxLength = 15
    )
    @Size(max = 15, message = "최대 15글자까지 가능합니다.")
    String nickname,

    @Schema(
        description = "사용자 이메일",
        example = "test@example.com",
        pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    )
    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
        message = "올바른 이메일 형식이 아닙니다."
    )
    String email,

    @Schema(
        description = "비밀번호 (대소문자 + 숫자 + 특수문자 포함, 최소 8자 이상)",
        example = "Abcd1234!",
        minLength = 8,
        pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$"
    )
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$",
        message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함되어야 합니다."
    )
    String password

) {}

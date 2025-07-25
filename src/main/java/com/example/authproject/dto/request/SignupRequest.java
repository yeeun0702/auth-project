package com.example.authproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
    @NotBlank(message = "유저 이름은 필수입니다.")
    @Size(max = 10, message = "최대 10글자까지 가능합니다.")
    String username,

    @Size(max = 15, message = "최대 15글자까지 가능합니다.")
    String nickname,

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
        message = "올바른 이메일 형식이 아닙니다."
    )
    String email,

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$",
        message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함되어야 합니다."
    )
    String password
) {}

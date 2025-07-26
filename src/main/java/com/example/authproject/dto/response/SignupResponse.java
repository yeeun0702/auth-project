package com.example.authproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "회원가입 응답 DTO")
public record SignupResponse(

    @Schema(description = "사용자 ID", example = "testUser")
    String username,

    @Schema(description = "사용자 닉네임", example = "홍길동")
    String nickname,

    @Schema(description = "회원에게 부여된 역할 리스트")
    List<RoleResponse> roles

) {
    @Schema(description = "역할 정보 DTO")
    public record RoleResponse(
        @Schema(description = "역할명", example = "USER")
        String role
    ) {}
}

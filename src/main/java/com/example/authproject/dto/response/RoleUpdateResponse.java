package com.example.authproject.dto.response;

import com.example.authproject.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "사용자 권한 변경 응답")
public record RoleUpdateResponse(

    @Schema(description = "사용자 ID", example = "testUser")
    String username,

    @Schema(description = "사용자 닉네임", example = "홍길동")
    String nickname,

    @Schema(description = "부여된 역할 목록", example = "[{\"role\": \"USER\"}, {\"role\": \"ADMIN\"}]")
    Set<Role> roles

) {}

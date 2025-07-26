package com.example.authproject.controller;

import com.example.authproject.common.exception.code.SuccessCode;
import com.example.authproject.common.response.ApiResponseDto;
import com.example.authproject.dto.response.RoleUpdateResponse;
import com.example.authproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "관리자 권한 관련 API")
public class UserController {

    private final UserService userService;

    @PatchMapping("/users/{userId}/roles")
    @Operation(
        summary = "관리자 권한 부여",
        description = "지정된 사용자의 역할(Role)을 ADMIN으로 변경합니다.",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    public ApiResponseDto<RoleUpdateResponse> promoteToAdmin(
        @Parameter(description = "권한을 부여할 사용자 ID", example = "1")
        @PathVariable Long userId
    ) {
        RoleUpdateResponse response = userService.promoteToAdmin(userId);
        return ApiResponseDto.success(SuccessCode.OK, response);
    }
}

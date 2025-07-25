package com.example.authproject.dto.response;

import java.util.List;

public record SignupResponse(
    String username,
    String nickname,
    List<RoleResponse> roles
) {
    public record RoleResponse(String role) {}
}

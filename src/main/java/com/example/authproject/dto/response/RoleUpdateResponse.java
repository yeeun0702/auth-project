package com.example.authproject.dto.response;

import com.example.authproject.domain.Role;
import java.util.Set;

public record RoleUpdateResponse(
    String username,
    String nickname,
    Set<Role> roles
) {}
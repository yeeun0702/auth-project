package com.example.authproject.service;

import com.example.authproject.common.exception.base.NotFoundException;
import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.domain.User;
import com.example.authproject.dto.response.RoleUpdateResponse;
import com.example.authproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public RoleUpdateResponse promoteToAdmin(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        user.promoteToAdmin();

        return new RoleUpdateResponse(
            user.getUsername(),
            user.getNickname(),
            user.getRoles()
        );
    }
}

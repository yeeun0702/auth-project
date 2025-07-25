package com.example.authproject.service;

import com.example.authproject.common.exception.base.BadRequestException;
import com.example.authproject.config.PasswordEncoder;
import com.example.authproject.domain.User;
import com.example.authproject.dto.request.SignupRequest;
import com.example.authproject.dto.response.SignupResponse;
import com.example.authproject.dto.response.SignupResponse.RoleResponse;
import com.example.authproject.repository.UserRepository;
import com.example.authproject.common.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_EXISTS);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());


        // User 엔티티 생성
        User user = new User(
            request.username(),
            request.nickname(),
            request.email(),
            encodedPassword
        );

        userRepository.save(user);

        return new SignupResponse(
            user.getUsername(),
            user.getNickname(),
            List.of(new RoleResponse("USER"))
        );
    }
}

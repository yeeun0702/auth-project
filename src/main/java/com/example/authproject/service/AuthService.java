package com.example.authproject.service;

import com.example.authproject.common.exception.base.BadRequestException;
import com.example.authproject.common.exception.base.NotFoundException;
import com.example.authproject.config.PasswordEncoder;
import com.example.authproject.domain.User;
import com.example.authproject.dto.request.LoginRequest;
import com.example.authproject.dto.request.SignupRequest;
import com.example.authproject.dto.response.LoginResponse;
import com.example.authproject.dto.response.SignupResponse;
import com.example.authproject.dto.response.SignupResponse.RoleResponse;
import com.example.authproject.repository.UserRepository;
import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // 유저 가입 여부 체크
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_EXISTS);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());

        // User 엔티티 생성
        User user = new User(
            request.username(),
            request.nickname(),
            request.email(), // email은 여전히 저장용으로는 유지
            encodedPassword
        );

        userRepository.save(user);

        return new SignupResponse(
            user.getUsername(),
            user.getNickname(),
            List.of(new RoleResponse("USER"))
        );
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // 유저 이름으로 조회
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_CREDENTIALS);
        }

        List<String> roles = List.of("USER");
        String token = jwtProvider.generateToken(user.getUsername(), roles);

        return new LoginResponse(token);
    }

}

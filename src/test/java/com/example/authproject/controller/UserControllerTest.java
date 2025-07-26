package com.example.authproject.controller;

import com.example.authproject.config.PasswordEncoder;
import com.example.authproject.domain.Role;
import com.example.authproject.domain.User;
import com.example.authproject.dto.request.LoginRequest;
import com.example.authproject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private Long targetUserId;

    @BeforeEach
    void setUp() {
        // 일반 사용자 생성
        User user = new User("targetUser", "user", "target@example.com", passwordEncoder.encode("Abcd1234!"));
        user.getRoles().add(Role.USER);
        userRepository.save(user);
        targetUserId = user.getUserId();

        // 관리자 사용자 생성
        User admin = new User("adminUser", "admin", "admin@example.com", passwordEncoder.encode("Abcd1234!"));
        admin.getRoles().add(Role.ADMIN);
        userRepository.save(admin);
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(username, password);
        String responseBody = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readTree(responseBody).get("data").get("token").asText();
    }

    @Test
    @DisplayName("관리자 계정이 다른 사용자에게 권한을 부여하면 성공")
    void 관리자_권한_부여_성공() throws Exception {
        String adminToken = loginAndGetToken("adminUser", "Abcd1234!");

        mockMvc.perform(patch("/api/admin/users/" + targetUserId + "/roles")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("일반 사용자가 관리자 API에 접근 시 403 Forbidden을 반환")
    void 일반사용자_권한_부여_실패() throws Exception {
        String userToken = loginAndGetToken("targetUser", "Abcd1234!");

        mockMvc.perform(patch("/api/admin/users/" + targetUserId + "/roles")
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error.code").value("ACCESS_DENIED"));
    }

    @Test
    @DisplayName("존재하지 않는 유저 ID로 권한 부여 시도 시 404 Not Found 반환")
    void 존재하지_않는_유저_권한_부여_실패() throws Exception {
        String adminToken = loginAndGetToken("adminUser", "Abcd1234!");

        mockMvc.perform(patch("/api/admin/users/9999/roles")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error.code").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("Authorization 헤더 없이 관리자 API 접근 시 401 Unauthorized 반환")
    void 인증없이_접근_실패() throws Exception {
        mockMvc.perform(patch("/api/admin/users/" + targetUserId + "/roles"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("잘못된 형식의 토큰으로 관리자 API 접근 시 401 Unauthorized 반환")
    void 잘못된형식_토큰_접근_실패() throws Exception {
        mockMvc.perform(patch("/api/admin/users/" + targetUserId + "/roles")
                .header("Authorization", "invalid-token"))
            .andExpect(status().isUnauthorized());
    }
}

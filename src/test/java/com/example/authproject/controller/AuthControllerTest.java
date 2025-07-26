package com.example.authproject.controller;

import com.example.authproject.dto.request.LoginRequest;
import com.example.authproject.dto.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공 - 유효한 입력 시 201 반환 및 사용자 정보 포함")
    void 회원가입_성공() throws Exception {
        SignupRequest request = new SignupRequest("testUser", "닉네임", "test@example.com", "Abcd1234!");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.username").value("testUser"))
            .andExpect(jsonPath("$.data.roles[0].role").value("USER"));
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 username")
    void 회원가입_중복_실패() throws Exception {
        SignupRequest request = new SignupRequest("duplicate", "닉네임", "dupe@example.com", "Abcd1234!");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"));
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 유효성 검증 실패")
    void 회원가입_비밀번호_유효성_실패() throws Exception {
        SignupRequest request = new SignupRequest("weakUser", "닉네임", "weak@example.com", "123");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 성공 - 가입된 계정으로 로그인 시 토큰 발급")
    void 로그인_성공() throws Exception {
        SignupRequest signup = new SignupRequest("loginUser", "닉네", "login@test.com", "Abcd1234!");
        mockMvc.perform(post("/api/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signup)));

        LoginRequest login = new LoginRequest("loginUser", "Abcd1234!");
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.token").exists());
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    void 로그인_실패_유저없음() throws Exception {
        LoginRequest login = new LoginRequest("noUser", "Abcd1234!");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error.code").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void 로그인_실패_비밀번호불일치() throws Exception {
        SignupRequest signup = new SignupRequest("existUser", "닉네임", "exist@example.com", "Correct123!");
        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signup)))
            .andExpect(status().isCreated());

        LoginRequest login = new LoginRequest("existUser", "Wrong1234!");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error.code").value("INVALID_CREDENTIALS"));
    }
}

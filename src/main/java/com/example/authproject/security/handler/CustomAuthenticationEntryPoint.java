package com.example.authproject.security.handler;

import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.common.response.ApiResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json;charset=UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponseDto<?> responseDto = ApiResponseDto.fail(ErrorCode.INVALID_TOKEN);
        String json = objectMapper.writeValueAsString(responseDto);

        response.getWriter().write(json);
    }
}
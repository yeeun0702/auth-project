package com.example.authproject.common.advice;

import com.example.authproject.common.exception.code.SuccessCode;
import com.example.authproject.common.response.ApiResponseDto;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseWrappingAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
        MethodParameter returnType,
        @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        @NonNull MethodParameter returnType,
        @NonNull MediaType selectedContentType,
        @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
        @NonNull ServerHttpRequest request,
        @NonNull ServerHttpResponse response
    ) {
        if (body instanceof ResponseEntity || body instanceof ApiResponseDto) {
            // ApiResponseDto라면 statusCode를 설정해줌
            if (body instanceof ApiResponseDto<?> dto) {
                response.setStatusCode(org.springframework.http.HttpStatus.valueOf(dto.statusCode()));
            }
            return body;
        }

        // 성공 응답 포맷으로 감싸고, HTTP 상태코드도 설정
        ApiResponseDto<?> wrapped = ApiResponseDto.success(SuccessCode.OK, body);
        response.setStatusCode(org.springframework.http.HttpStatus.valueOf(wrapped.statusCode()));
        return wrapped;
    }
}

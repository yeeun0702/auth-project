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
        String path = request.getURI().getPath();

        // Swagger 관련 요청은 감싸지 않고 그대로 반환
        if (path.startsWith("/v3/api-docs") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/swagger-resources")) {
            return body;
        }

        if (body instanceof ResponseEntity || body instanceof ApiResponseDto) {
            if (body instanceof ApiResponseDto<?> dto) {
                response.setStatusCode(org.springframework.http.HttpStatus.valueOf(dto.statusCode()));
            }
            return body;
        }

        ApiResponseDto<?> wrapped = ApiResponseDto.success(SuccessCode.OK, body);
        response.setStatusCode(org.springframework.http.HttpStatus.valueOf(wrapped.statusCode()));
        return wrapped;
    }

}

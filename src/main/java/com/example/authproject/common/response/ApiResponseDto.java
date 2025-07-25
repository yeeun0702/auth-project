package com.example.authproject.common.response;

import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.common.exception.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * 모든 API 응답을 감싸는 공통 응답 DTO
 */
@Builder
public record ApiResponseDto<T>(
    LocalDateTime timestamp,                       // 응답 시간
    int statusCode,                                // HTTP 상태 코드
    @JsonInclude(NON_NULL) String message,         // 성공 메시지
    @JsonInclude(NON_NULL) T data,                 // 성공 데이터
    @JsonInclude(NON_NULL) ErrorDetail error       // 실패 정보
) {

    /**
     * 성공 응답 생성
     */
    public static <T> ApiResponseDto<T> success(SuccessCode code, T data) {
        return ApiResponseDto.<T>builder()
            .timestamp(LocalDateTime.now())
            .statusCode(code.getHttpStatus().value())
            .message(code.getMessage())
            .data(data)
            .build();
    }

    /**
     * 실패 응답 생성 (ErrorCode 기반)
     */
    public static <T> ApiResponseDto<T> fail(ErrorCode code) {
        return ApiResponseDto.<T>builder()
            .timestamp(LocalDateTime.now())
            .statusCode(code.getHttpStatus().value())
            .error(new ErrorDetail(code.name(), code.getMessage()))
            .build();
    }

    /**
     * 실패 응답 생성 (ErrorCode + 커스텀 메시지)
     */
    public static <T> ApiResponseDto<T> fail(ErrorCode code, String customMessage) {
        return ApiResponseDto.<T>builder()
            .timestamp(LocalDateTime.now())
            .statusCode(code.getHttpStatus().value())
            .error(new ErrorDetail(code.name(), customMessage))
            .build();
    }

    /**
     * 에러 상세 정보를 담는 내부 클래스
     */
    public record ErrorDetail(String code, String message) {}
}

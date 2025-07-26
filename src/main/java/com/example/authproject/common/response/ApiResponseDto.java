package com.example.authproject.common.response;

import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.common.exception.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * 모든 API 응답을 감싸는 공통 응답 DTO
 */
@Builder
@Schema(description = "모든 API 응답의 공통 구조")
public record ApiResponseDto<T>(

    @Schema(description = "응답 시간 (서버 기준)", example = "2025-07-26T22:34:12.123")
    LocalDateTime timestamp,

    @Schema(description = "HTTP 상태 코드", example = "200")
    int statusCode,

    @Schema(description = "성공 메시지", example = "회원가입 성공", nullable = true)
    @JsonInclude(NON_NULL)
    String message,

    @Schema(description = "응답 데이터", nullable = true)
    @JsonInclude(NON_NULL)
    T data,

    @Schema(description = "에러 상세 정보", nullable = true)
    @JsonInclude(NON_NULL)
    ErrorDetail error

) {

    public static <T> ApiResponseDto<T> success(SuccessCode code, T data) {
        return ApiResponseDto.<T>builder()
            .timestamp(LocalDateTime.now())
            .statusCode(code.getHttpStatus().value())
            .message(code.getMessage())
            .data(data)
            .build();
    }

    public static <T> ApiResponseDto<T> fail(ErrorCode code) {
        return ApiResponseDto.<T>builder()
            .timestamp(LocalDateTime.now())
            .statusCode(code.getHttpStatus().value())
            .error(new ErrorDetail(code.name(), code.getMessage()))
            .build();
    }

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
    @Schema(description = "에러 응답 상세 정보")
    public record ErrorDetail(

        @Schema(description = "에러 코드 (Enum name)", example = "DUPLICATED_USERNAME")
        String code,

        @Schema(description = "에러 메시지", example = "이미 사용 중인 사용자 이름입니다.")
        String message

    ) {}
}

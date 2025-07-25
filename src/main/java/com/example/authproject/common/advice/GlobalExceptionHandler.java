package com.example.authproject.common.advice;

import com.example.authproject.common.exception.base.CustomException;
import com.example.authproject.common.exception.code.ErrorCode;
import com.example.authproject.common.response.ApiResponseDto;;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // @Valid 검증 실패 (DTO 필드 유효성 검사 실패 시)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("Validation failed: {}", e.getMessage());
        String message = e.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(fieldError -> fieldError.getDefaultMessage())
            .orElse("잘못된 요청입니다.");
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(ErrorCode.INVALID_PARAMETER));
    }

    // @RequestParam 등의 타입 불일치 (예: Long 자리에 문자열)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDto<?>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.error("Type mismatch: {}", e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(ErrorCode.BAD_REQUEST));
    }

    // Spring 6 이상에서 메서드 단위 유효성 검증 실패 시
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponseDto<?>> handleValidationException(HandlerMethodValidationException e) {
        log.error("Validation exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(ErrorCode.BAD_REQUEST));
    }

    // @RequestHeader 누락 (예: Authorization 헤더 없음)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponseDto<?>> handleMissingHeader(MissingRequestHeaderException e) {
        log.error("Missing header: {}", e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(ErrorCode.MISSING_PARAMETER));
    }

    // 필수 요청 파라미터 누락 (예: @RequestParam required=true인데 없음)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDto<?>> handleMissingParam(MissingServletRequestParameterException e) {
        log.error("Missing parameter: {}", e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(ErrorCode.INVALID_PARAMETER));
    }

    // 잘못된 JSON 포맷 또는 역직렬화 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDto<?>> handleUnreadable(HttpMessageNotReadableException e) {
        log.error("Unreadable body: {}", e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseDto.fail(ErrorCode.BAD_REQUEST));
    }

    // 존재하지 않는 URL에 접근했을 때
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> handleNotFound(NoHandlerFoundException e) {
        log.error("No handler found: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.fail(ErrorCode.NOT_FOUND));
    }

    // 지원하지 않는 HTTP 메서드로 요청 시 (예: POST만 지원하는데 GET 요청)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponseDto<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("Method not supported: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiResponseDto.fail(ErrorCode.METHOD_NOT_ALLOWED));
    }

    // 개발자가 정의한 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto<?>> handleCustom(CustomException e) {
        log.error("Custom exception: {}", e.getMessage());
        return ResponseEntity.status(e.getHttpStatus())
            .body(ApiResponseDto.fail(e.getErrorCode()));
    }

    // 위에서 처리되지 않은 모든 예외 처리 (예: NullPointerException 등)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(Exception e) {
        log.error("Unhandled exception: {} - {}", e.getClass(), e.getMessage());
        return ResponseEntity.internalServerError().body(ApiResponseDto.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}

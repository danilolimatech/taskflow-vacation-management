package com.taskflow.vacation.management.common.exception.handler;

import com.taskflow.vacation.management.common.exception.domain.ForbiddenException;
import com.taskflow.vacation.management.common.exception.dto.ErrorResponse;
import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private String sanitize(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }

    private Object[] sanitizeArgs(Object[] args) {
        if (args == null) return null;
        Object[] sanitized = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            sanitized[i] = args[i] instanceof String ? sanitize((String) args[i]) : args[i];
        }
        return sanitized;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, Locale locale, HttpServletRequest request) {
        String message = messageSource.getMessage(ex.getMessageKey(), sanitizeArgs(ex.getArgs()), locale);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(HttpStatus.NOT_FOUND, message, request.getRequestURI()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, Locale locale, HttpServletRequest request) {
        String message = messageSource.getMessage(ex.getMessageKey(), sanitizeArgs(ex.getArgs()), locale);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(HttpStatus.CONFLICT, message, request.getRequestURI()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, Locale locale, HttpServletRequest request) {
        String message = messageSource.getMessage(ex.getMessageKey(), sanitizeArgs(ex.getArgs()), locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, message, request.getRequestURI()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex, Locale locale, HttpServletRequest request) {
        String message = messageSource.getMessage(ex.getMessageKey(), sanitizeArgs(ex.getArgs()), locale);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of(HttpStatus.FORBIDDEN, message, request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, Locale locale, HttpServletRequest request) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage(), (a, b) -> a));
        String message = messageSource.getMessage("validation.failed", null, locale);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.ofValidation(HttpStatus.UNPROCESSABLE_ENTITY, message, request.getRequestURI(), fields));
    }
}

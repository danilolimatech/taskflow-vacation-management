package com.taskflow.vacation.management.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        Map<String, String> fields
) {
    public static ErrorResponse of(HttpStatus httpStatus, String message, String path) {
        return new ErrorResponse(httpStatus.value(), httpStatus.name(), message, path, Instant.now(), null);
    }

    public static ErrorResponse ofValidation(HttpStatus httpStatus, String message, String path, Map<String, String> fields) {
        return new ErrorResponse(httpStatus.value(), httpStatus.name(), message, path, Instant.now(), fields);
    }
}

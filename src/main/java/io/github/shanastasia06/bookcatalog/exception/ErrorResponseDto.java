package io.github.shanastasia06.bookcatalog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDto(
        int status,
        String error,
        String message,
        LocalDateTime timestamp,
        Map<String, String> validationErrors // ошибки из @Valid
) {
    public ErrorResponseDto(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now(), null);
    }

    public ErrorResponseDto(int status, String error, String message, Map<String, String> validationErrors) {
        this(status, error, message, LocalDateTime.now(), validationErrors);
    }
}

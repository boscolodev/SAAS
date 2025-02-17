package com.gbs.msauthentication.api.handler;

import com.gbs.msauthentication.api.ApiError;
import com.gbs.msauthentication.api.ValidationError;
import com.gbs.msauthentication.api.exception.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError generalException(Exception e, HttpServletRequest request) {
        log.error("GlobalExceptionHandler::DatabaseException: {}", e);
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("General Error: " + e.getClass().getSimpleName())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError geralException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("GlobalExceptionHandler::MethodArgumentNotValidException {}: ", e);

        ValidationError error = new ValidationError();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Method Argument NotValid Exception");
        error.setPath(request.getRequestURI());

        StringBuilder errorMessage = new StringBuilder();
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
            errorMessage.append(f.getField()).append(": ").append(f.getDefaultMessage()).append("; ");
        }

        error.setMessage(errorMessage.toString().trim()); // Definindo a mensagem corretamente
        return error;
    }

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError databaseError(DatabaseException e, HttpServletRequest request) {
        log.error("GlobalExceptionHandler::DatabaseException: {}", e);
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Database Error")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

}

package com.gbs.msauthentication.api.handler;

import com.gbs.msauthentication.api.ApiError;
import com.gbs.msauthentication.api.exception.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError databaseError(DatabaseException e, HttpServletRequest request) {
//        return new ApiError(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(),
//                e.getMessage(),
//                "Resource not found",
//                request.getRequestURI()
//                );
        return ApiError.builder()
                .build();
    }
}

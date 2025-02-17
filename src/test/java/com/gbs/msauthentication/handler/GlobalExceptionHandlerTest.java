package com.gbs.msauthentication.handler;

import com.gbs.msauthentication.api.ApiError;
import com.gbs.msauthentication.api.exception.DatabaseException;
import com.gbs.msauthentication.api.handler.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testGeneralException() {
        Exception exception = new Exception("General error");
        when(request.getRequestURI()).thenReturn("/test");

        ApiError apiError = globalExceptionHandler.generalException(exception, request);

        assertEquals(HttpStatus.NOT_FOUND.value(), apiError.getStatus());
        assertEquals("General Error: Exception", apiError.getError());
        assertEquals("General error", apiError.getMessage());
        assertEquals("/test", apiError.getPath());
    }

    @Test
    void testDatabaseException() {
        DatabaseException exception = new DatabaseException("Database error");
        when(request.getRequestURI()).thenReturn("/test");

        ApiError apiError = globalExceptionHandler.databaseError(exception, request);

        assertEquals(HttpStatus.NOT_FOUND.value(), apiError.getStatus());
        assertEquals("Database Error", apiError.getError());
        assertEquals("Database error", apiError.getMessage());
        assertEquals("/test", apiError.getPath());
    }

    @Test
    void testMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "Validation error message");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(bindingResult.getFieldError()).thenReturn(fieldError); // Adicionado para evitar NullPointerException
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(request.getRequestURI()).thenReturn("/test");

        ApiError apiError = globalExceptionHandler.geralException(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.getStatus());
        assertEquals("Method Argument NotValid Exception", apiError.getError());
        assertEquals("fieldName: Validation error message;", apiError.getMessage());
        assertEquals("/test", apiError.getPath());
    }

}
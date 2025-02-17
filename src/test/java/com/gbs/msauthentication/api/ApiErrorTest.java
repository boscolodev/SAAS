package com.gbs.msauthentication.api;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiErrorTest {

    @Test
    void testApiError() {
        LocalDateTime now = LocalDateTime.now();
        ApiError apiError = new ApiError(now, 404, "Not Found", "Resource not found", "/test");

        assertEquals(now, apiError.getTimestamp());
        assertEquals(404, apiError.getStatus());
        assertEquals("Not Found", apiError.getError());
        assertEquals("Resource not found", apiError.getMessage());
        assertEquals("/test", apiError.getPath());
    }
}
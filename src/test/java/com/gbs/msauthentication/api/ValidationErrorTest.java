package com.gbs.msauthentication.api;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationErrorTest {

    @Test
    void testValidationError() {
        LocalDateTime now = LocalDateTime.now();
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(now);
        validationError.setStatus(400);
        validationError.setError("Validation Error");
        validationError.setMessage("Validation failed");
        validationError.setPath("/test");

        validationError.addError("field1", "must not be null");
        validationError.addError("field2", "must be a valid email");

        assertEquals(now, validationError.getTimestamp());
        assertEquals(400, validationError.getStatus());
        assertEquals("Validation Error", validationError.getError());
        assertEquals("Validation failed", validationError.getMessage());
        assertEquals("/test", validationError.getPath());

        assertEquals(2, validationError.getErrors().size());
        assertTrue(validationError.getErrors().stream().anyMatch(e -> e.getFieldName().equals("field1") && e.getMessage().equals("must not be null")));
        assertTrue(validationError.getErrors().stream().anyMatch(e -> e.getFieldName().equals("field2") && e.getMessage().equals("must be a valid email")));
    }
}

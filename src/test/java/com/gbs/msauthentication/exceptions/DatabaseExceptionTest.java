package com.gbs.msauthentication.exceptions;

import com.gbs.msauthentication.api.exception.DatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Database error occurred";
        DatabaseException exception = assertThrows(DatabaseException.class, () -> {
            throw new DatabaseException(errorMessage);
        });
        assertEquals(errorMessage, exception.getMessage());
    }
}
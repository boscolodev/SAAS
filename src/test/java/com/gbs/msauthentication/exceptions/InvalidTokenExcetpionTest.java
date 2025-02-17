package com.gbs.msauthentication.exceptions;

import com.gbs.msauthentication.api.exception.DatabaseException;
import com.gbs.msauthentication.api.exception.InvalidTokenExcetpion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidTokenExcetpionTest {
    @Test
    void testExceptionMessage() {
        String errorMessage = "InvalidToken exception occurred";
        InvalidTokenExcetpion exception = assertThrows(InvalidTokenExcetpion.class, () -> {
            throw new InvalidTokenExcetpion(errorMessage);
        });
        assertEquals(errorMessage, exception.getMessage());
    }
}

package com.gbs.msauthentication.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginResponseTest {

    @Test
    void testLoginResponse() {
        LoginResponse response = new LoginResponse("token123", "user@example.com");

        assertEquals("token123", response.token());
        assertEquals("user@example.com", response.email());
    }
}

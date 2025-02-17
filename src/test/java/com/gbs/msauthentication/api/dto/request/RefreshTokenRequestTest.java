package com.gbs.msauthentication.api.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefreshTokenRequestTest {
    @Test
    void testRefreshTokenRequest() {
        RefreshTokenRequest request = new RefreshTokenRequest("refreshToken123");

        assertEquals("refreshToken123", request.refreshToken());
    }
}

package com.gbs.msauthentication.api.dto.response;

import com.gbs.msauthentication.api.FieldMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefreshTokenResponseTest {

    @Test
    void testRefeshTokenResponse() {
        RefeshTokenResponse response = new RefeshTokenResponse("accessToken123", "refreshToken456", "user@example.com");

        assertEquals("accessToken123", response.accessToken());
        assertEquals("refreshToken456", response.refreshToken());
        assertEquals("user@example.com", response.email());
    }
}

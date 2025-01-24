package com.gbs.msauthentication.api.dto.response;

public record RefeshTokenResponse(String accessToken, String refreshToken, String email) {
}

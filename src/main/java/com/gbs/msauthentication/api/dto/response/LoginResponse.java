package com.gbs.msauthentication.api.dto.response;

public record LoginResponse(
        String token,
        String email) {
}

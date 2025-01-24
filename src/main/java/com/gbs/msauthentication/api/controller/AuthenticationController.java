package com.gbs.msauthentication.api.controller;

import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.response.LoginResponse;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.RefeshTokenResponse;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.usecase.AuthenticationUseCase;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;
    private static final String BEARER_PREFIX = "Bearer ";


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = authenticationUseCase.login(loginRequest);
        response.setHeader("Authorization", BEARER_PREFIX + loginResponse.token());
        response.setHeader("Email", loginResponse.email());
        return loginResponse;
    }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO userRequestDTO) {
        return authenticationUseCase.register(userRequestDTO);
    }

    @PostMapping("/refresh-token")
    public RefeshTokenResponse refreshToken(@RequestHeader("Authorization") String authorizationHeader, HttpServletResponse response) {
        String refreshToken = authorizationHeader.replace(BEARER_PREFIX, "");
        RefeshTokenResponse refeshTokenResponse = authenticationUseCase.refreshToken(refreshToken);
        response.setHeader("Authorization", BEARER_PREFIX + refeshTokenResponse.accessToken());
        response.setHeader("Refresh-Token", refeshTokenResponse.refreshToken());
        response.setHeader("Email", refeshTokenResponse.email());
        return refeshTokenResponse;
    }
}

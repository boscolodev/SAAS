package com.gbs.msauthentication.usecase;

import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.response.LoginResponse;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.RefeshTokenResponse;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;

public interface AuthenticationUseCase {
    LoginResponse login(final LoginRequest loginRequest);
    UserResponseDTO register(final UserRequestDTO userRequestDTO);
    RefeshTokenResponse refreshToken(final String token);
}

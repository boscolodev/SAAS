package com.gbs.msauthentication;

import com.gbs.msauthentication.api.dto.AddressDTO;
import com.gbs.msauthentication.api.dto.UserDataDTO;
import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.request.RefreshTokenRequest;
import com.gbs.msauthentication.api.dto.request.RoleRequestDTO;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;

import java.util.List;
import java.util.Set;

public class DTOFactory {

    public static LoginRequest createValidLoginRequest() {
        return new LoginRequest("user@example.com", "password123");
    }

    public static LoginRequest createInvalidLoginRequest() {
        return new LoginRequest("invalid-email", "");
    }

    public static RefreshTokenRequest createValidRefreshTokenRequest() {
        return new RefreshTokenRequest("validRefreshToken");
    }

    public static RefreshTokenRequest createInvalidRefreshTokenRequest() {
        return new RefreshTokenRequest(null);
    }

    public static RoleRequestDTO createValidRoleRequestDTO() {
        return new RoleRequestDTO("ADMIN");
    }

    public static RoleRequestDTO createInvalidRoleRequestDTO() {
        return new RoleRequestDTO("");
    }

    public static UserRequestDTO createValidUserRequestDTO() {
        return new UserRequestDTO(
                "user@example.com",
                "password123",
                "ACTIVE",
                new UserDataDTO(),  // Assumindo que o construtor padrão seja válido
                Set.of(createValidRoleRequestDTO()),
                List.of(new AddressDTO()) // Assumindo que o construtor padrão seja válido
        );
    }

    public static UserRequestDTO createInvalidUserRequestDTO() {
        return new UserRequestDTO(
                "",
                "",
                "",
                null,
                null,
                null
        );
    }
}

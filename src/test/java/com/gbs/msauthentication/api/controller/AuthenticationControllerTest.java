package com.gbs.msauthentication.api.controller;

import com.gbs.msauthentication.DTOFactory;
import com.gbs.msauthentication.ModelFactory;
import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.response.LoginResponse;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.RefeshTokenResponse;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.model.User;
import com.gbs.msauthentication.usecase.AuthenticationUseCase;
import com.gbs.msauthentication.util.MapperUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationUseCase authenticationUseCase;

    @InjectMocks
    private AuthenticationController authenticationController;

    private static final String BEARER_PREFIX = "Bearer ";

    @BeforeEach
    void setUp() {
        // Configuração inicial se necessário
    }

    @Test
    void login_ShouldReturnLoginResponseAndSetHeaders() {
        // Given
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password123");
        LoginResponse loginResponse = new LoginResponse("token123", "user@example.com");

        MockHttpServletResponse response = new MockHttpServletResponse();
        when(authenticationUseCase.login(loginRequest)).thenReturn(loginResponse);

        // When
        LoginResponse result = authenticationController.login(loginRequest, response);

        // Then
        assertNotNull(result);
        assertEquals("token123", result.token());
        assertEquals("user@example.com", result.email());

        assertEquals(BEARER_PREFIX + "token123", response.getHeader("Authorization"));
        assertEquals("user@example.com", response.getHeader("Email"));

        verify(authenticationUseCase, times(1)).login(loginRequest);
    }

    @Test
    void register_ShouldReturnUserResponseDTO() {
        // Given
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        User user = ModelFactory.createUser();
        UserResponseDTO expectedResponse = MapperUtil.converte(user, UserResponseDTO.class);

        when(authenticationUseCase.register(userRequestDTO)).thenReturn(expectedResponse);

        UserResponseDTO result = authenticationController.register(userRequestDTO);

        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());

        verify(authenticationUseCase, times(1)).register(userRequestDTO);
    }

    @Test
    void refreshToken_ShouldReturnRefeshTokenResponseAndSetHeaders() {
        // Given
        String refreshToken = "refreshToken123";
        RefeshTokenResponse refeshTokenResponse = new RefeshTokenResponse("newAccessToken", "newRefreshToken", "user@example.com");

        MockHttpServletResponse response = new MockHttpServletResponse();
        when(authenticationUseCase.refreshToken(refreshToken)).thenReturn(refeshTokenResponse);

        // When
        RefeshTokenResponse result = authenticationController.refreshToken(BEARER_PREFIX + refreshToken, response);

        // Then
        assertNotNull(result);
        assertEquals("newAccessToken", result.accessToken());
        assertEquals("newRefreshToken", result.refreshToken());
        assertEquals("user@example.com", result.email());

        assertEquals(BEARER_PREFIX + "newAccessToken", response.getHeader("Authorization"));
        assertEquals("newRefreshToken", response.getHeader("Refresh-Token"));
        assertEquals("user@example.com", response.getHeader("Email"));

        verify(authenticationUseCase, times(1)).refreshToken(refreshToken);
    }
}

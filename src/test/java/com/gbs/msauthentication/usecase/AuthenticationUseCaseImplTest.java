package com.gbs.msauthentication.usecase;

import com.gbs.msauthentication.DTOFactory;
import com.gbs.msauthentication.ModelFactory;
import com.gbs.msauthentication.api.dto.UserDataDTO;
import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.LoginResponse;
import com.gbs.msauthentication.api.dto.response.RefeshTokenResponse;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.api.exception.InvalidTokenExcetpion;
import com.gbs.msauthentication.security.JwtUtil;
import com.gbs.msauthentication.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthenticationUseCaseImpl authenticationUseCase;

    private LoginRequest loginRequest;
    private UserRequestDTO userRequestDTO;
    private UserDetails userDetails;
    private final String token = "mockedToken";
    private final String refreshToken = "mockedRefreshToken";

    @BeforeEach
    void setUp() {
        userRequestDTO = MapperUtil.converte(ModelFactory.createUser(), UserRequestDTO.class);
        userDetails = mock(UserDetails.class);
        loginRequest = DTOFactory.createValidLoginRequest();
    }

    @Test
    void testLoginSuccess() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);

        LoginResponse response = authenticationUseCase.login(loginRequest);

        assertNotNull(response);
        assertEquals(token, response.token());
        assertEquals("user@example.com", response.email());
        verify(jwtUtil).generateToken(userDetails);
    }

    @Test
    void testLoginFailure() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> authenticationUseCase.login(loginRequest));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        when(userUseCase.register(any(UserRequestDTO.class)))
                .thenThrow(new RuntimeException("User already exists"));

        assertThrows(RuntimeException.class, () -> authenticationUseCase.register(userRequestDTO));
    }

    @Test
    void testRegister() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        UserResponseDTO expectedResponse = new UserResponseDTO("test@example.com", "ACTIVE", new UserDataDTO(), Set.of(), List.of());
        when(userUseCase.register(any(UserRequestDTO.class))).thenReturn(expectedResponse);

        UserResponseDTO response = authenticationUseCase.register(userRequestDTO);

        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
        verify(passwordEncoder).encode("123456");
        verify(userUseCase).register(userRequestDTO);
    }

    @Test
    void testRefreshTokenSuccess() {
        when(jwtUtil.isValid(refreshToken)).thenReturn(true);
        when(jwtUtil.extractUsername(refreshToken)).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);

        RefeshTokenResponse response = authenticationUseCase.refreshToken(refreshToken);

        assertNotNull(response);
        assertEquals(token, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
        assertEquals("test@example.com", response.email());
        verify(jwtUtil).isValid(refreshToken);
    }

    @Test
    void testRefreshTokenFailure() {
        when(jwtUtil.isValid(refreshToken)).thenReturn(false);

        assertThrows(InvalidTokenExcetpion.class, () -> authenticationUseCase.refreshToken(refreshToken));
    }
}

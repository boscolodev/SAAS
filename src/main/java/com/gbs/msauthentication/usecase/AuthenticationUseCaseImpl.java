package com.gbs.msauthentication.usecase;

import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.response.LoginResponse;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.RefeshTokenResponse;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.api.exception.InvalidTokenExcetpion;
import com.gbs.msauthentication.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationUseCaseImpl implements AuthenticationUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserUseCase userUseCase;
    private final UserDetailsService userDetailsService;

    public LoginResponse login(final LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            return new LoginResponse(token, loginRequest.email());

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    public UserResponseDTO register(final UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return userUseCase.register(userRequestDTO);
    }

    @Transactional
    public RefeshTokenResponse refreshToken(final String refreshToken) {
        if(jwtUtil.validateRefreshToken(refreshToken)){
            String email = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails);
            return new RefeshTokenResponse(token,refreshToken, email);
        } else {
            throw new InvalidTokenExcetpion("Invalid token");
        }
    }
}
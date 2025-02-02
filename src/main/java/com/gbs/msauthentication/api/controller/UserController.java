package com.gbs.msauthentication.api.controller;

import com.gbs.msauthentication.api.dto.request.LoginRequest;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.LoginResponse;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserUseCase userUseCase;
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
//            );
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String jwt = jwtUtil.generateToken(userDetails);
//
//            return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getUsername()));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais do usuário inválidas");
//        } catch (DisabledException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário desativado");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
//        }
//    }

    @GetMapping("/findAll")
    public Page<UserResponseDTO> findAllPaged(final Pageable pageable){
        log.warn("Listing all users");
        return userUseCase.findAll(pageable);
    }

    @GetMapping("/{email}")
    public UserResponseDTO findByEmail(@PathVariable final String email){
        log.warn("Listing user by email");
        return userUseCase.findByEmail(email);
    }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody final UserRequestDTO userRequestDTO){
        log.warn("Registering user");
        return userUseCase.register(userRequestDTO);
    }
}

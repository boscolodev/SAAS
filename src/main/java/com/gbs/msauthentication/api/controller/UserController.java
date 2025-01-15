package com.gbs.msauthentication.api.controller;

import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserUseCase userUseCase;

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

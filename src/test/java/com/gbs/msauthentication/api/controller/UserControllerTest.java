package com.gbs.msauthentication.api.controller;

import com.gbs.msauthentication.DTOFactory;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserUseCase userUseCase;

    private UserResponseDTO userResponseDTO;
    private UserRequestDTO userRequestDTO;
    private Pageable pageable;
    private Page<UserResponseDTO> userPage;

    @BeforeEach
    void setUp() {
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail("user@example.com.br");
        userRequestDTO = DTOFactory.createValidUserRequestDTO();
        pageable = PageRequest.of(0, 10);
        userPage = new PageImpl<>(List.of(userResponseDTO));
    }

    @Test
    void findAllPaged_ShouldReturnPagedUsers() {
        when(userUseCase.findAll(any(Pageable.class))).thenReturn(userPage);

        Page<UserResponseDTO> response = userController.findAllPaged(pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(userUseCase, times(1)).findAll(pageable);
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        when(userUseCase.findByEmail(anyString())).thenReturn(userResponseDTO);

        UserResponseDTO response = userController.findByEmail("user@example.com.br");

        assertNotNull(response);
        assertEquals("user@example.com.br", response.getEmail());
        verify(userUseCase, times(1)).findByEmail("user@example.com.br");
    }

    @Test
    void register_ShouldReturnRegisteredUser() {
        when(userUseCase.register(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        UserResponseDTO response = userController.register(userRequestDTO);

        assertNotNull(response);
        assertEquals("user@example.com.br", response.getEmail());
        verify(userUseCase, times(1)).register(userRequestDTO);
    }
}


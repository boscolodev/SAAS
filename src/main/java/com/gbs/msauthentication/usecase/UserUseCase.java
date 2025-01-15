package com.gbs.msauthentication.usecase;

import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserUseCase {
    Page<UserResponseDTO> findAll(final Pageable pageable);
    UserResponseDTO findByEmail(final String email);
    UserResponseDTO register(final UserRequestDTO userRequestDTO);
}

package com.gbs.msauthentication.usecase;

import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.api.exception.DatabaseException;
import com.gbs.msauthentication.model.Role;
import com.gbs.msauthentication.model.User;
import com.gbs.msauthentication.repositories.RoleRepository;
import com.gbs.msauthentication.repositories.UserRepository;
import com.gbs.msauthentication.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> MapperUtil.converte(user, UserResponseDTO.class));
    }

    public UserResponseDTO findByEmail(String email) {

        return MapperUtil.converte(userRepository.findByEmail(email)
                .orElseThrow(() -> new DatabaseException("User not found"))
                , UserResponseDTO.class);
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        this.isUserRegistered(userRequestDTO.getEmail(), userRequestDTO.getUserData().getCpfCnpj());

        Set<Role> roles = new HashSet<>();
        userRequestDTO.getRoles().forEach(roleRequestDTO -> {
            Role role = findRoleByRole(roleRequestDTO.getRole());
            roles.add(role);
        });

        userRequestDTO.getAddresses().forEach(addressDTO -> {
            if(Strings.isBlank(addressDTO.getId())){
                addressDTO.setId(UUID.randomUUID().toString());
            }
        });

        User user = MapperUtil.converte(userRequestDTO, User.class);
        user.setRoles(roles);

        return MapperUtil.converte(userRepository.save(user), UserResponseDTO.class);
    }

    private void isUserRegistered(String email, String documento) {
        userRepository.findByEmailOrUserData_CpfCnpj(email, documento).ifPresent(user -> {
            if (user.getEmail().equals(email)) {
                throw new DatabaseException("Email already registered");
            }
            if (user.getUserData().getCpfCnpj().equals(documento)) {
                throw new DatabaseException("Cpf/CNpj already registered");
            }
        });
    }

    private Role findRoleByRole(String role){
        return roleRepository.findByRole(role).orElseThrow(() -> new DatabaseException("Role not found"));
    }


}

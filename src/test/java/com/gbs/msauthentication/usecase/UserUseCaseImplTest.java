package com.gbs.msauthentication.usecase;

import com.gbs.msauthentication.DTOFactory;
import com.gbs.msauthentication.ModelFactory;
import com.gbs.msauthentication.api.dto.request.UserRequestDTO;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.api.exception.DatabaseException;
import com.gbs.msauthentication.gateways.RoleGateway;
import com.gbs.msauthentication.gateways.UserGateway;
import com.gbs.msauthentication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @BeforeEach
    void setUp() {
        lenient().when(userGateway.findByEmailOrUserData_CpfCnpj(anyString(), anyString()))
                .thenReturn(Optional.empty());
        lenient().when(roleGateway.findByRole(anyString())).thenReturn(Optional.empty());
    }

    @Test
    void findAllShouldReturnPageOfUsers() {
        Page<User> usersPage = new PageImpl<>(Collections.singletonList(ModelFactory.createUser()));
        when(userGateway.findAll(any(Pageable.class))).thenReturn(usersPage);

        Page<UserResponseDTO> result = userUseCase.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findByEmailShouldReturnUserWhenUserExists() {
        User user = ModelFactory.createUser();
        when(userGateway.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserResponseDTO result = userUseCase.findByEmail("user@example.com");

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void findByEmailShouldThrowDatabaseExceptionWhenUserNotFound() {
        when(userGateway.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(DatabaseException.class, () -> userUseCase.findByEmail("notfound@example.com"));
    }

    @Test
    void registerShouldReturnUserResponseDTOWhenSuccessful() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();
        User user = ModelFactory.createUser();
        when(userGateway.save(any(User.class))).thenReturn(user);
        when(roleGateway.findByRole(anyString())).thenReturn(Optional.of(ModelFactory.createRole()));

        UserResponseDTO result = userUseCase.register(requestDTO);

        assertNotNull(result);
        assertEquals(requestDTO.getEmail(), result.getEmail());
    }

    @Test
    void registerShouldThrowDatabaseExceptionWhenEmailAlreadyExists() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();
        when(userGateway.findByEmailOrUserData_CpfCnpj(eq(requestDTO.getEmail()), nullable(String.class)))
                .thenReturn(Optional.of(ModelFactory.createUser()));
        assertThrows(DatabaseException.class, () -> userUseCase.register(requestDTO));
    }

    @Test
    void register_ShouldThrowDatabaseExceptionWhenRoleNotFound() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();
        when(roleGateway.findByRole(anyString())).thenReturn(Optional.empty());

        assertThrows(DatabaseException.class, () -> userUseCase.register(requestDTO));
    }

    @Test
    void registerShouldThrowDatabaseExceptionWhenCpfCnpjAlreadyExists() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();
        requestDTO.setEmail("novoemail@example.com");
        requestDTO.getUserData().setCpfCnpj("12345678900");

        User existingUser = ModelFactory.createUser();
        existingUser.setEmail("diferente@example.com");
        existingUser.getUserData().setCpfCnpj("12345678900");

        when(userGateway.findByEmailOrUserData_CpfCnpj(anyString(), eq(requestDTO.getUserData().getCpfCnpj())))
                .thenReturn(Optional.of(existingUser));

        DatabaseException thrown = assertThrows(DatabaseException.class, () -> userUseCase.register(requestDTO));

        assertEquals("Cpf/CNpj already registered", thrown.getMessage());
    }

    @Test
    void registerShouldGenerateIdForBlankAddressId() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();

        requestDTO.getAddresses().get(0).setId(null);

        User user = ModelFactory.createUser();
        when(userGateway.save(any(User.class))).thenReturn(user);
        when(roleGateway.findByRole(anyString())).thenReturn(Optional.of(ModelFactory.createRole()));

        UserResponseDTO result = userUseCase.register(requestDTO);

        assertNotNull(result);
        assertNotNull(result.getAddresses().get(0).getId(), "O ID do endereço deveria ter sido gerado");
    }

    @Test
    void registerShouldNotChangeExistingAddressId() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();

        requestDTO.getAddresses().get(0).setId("existing-id");

        User user = ModelFactory.createUser();
        when(userGateway.save(any(User.class))).thenReturn(user);
        when(roleGateway.findByRole(anyString())).thenReturn(Optional.of(ModelFactory.createRole()));

        UserResponseDTO result = userUseCase.register(requestDTO);

        assertNotNull(result);
        assertEquals("1", result.getAddresses().get(0).getId(), "O ID do endereço não deveria ser alterado");
    }

    @Test
    void registerShouldNotThrowExceptionWhenCpfCnpjIsDifferent() {
        UserRequestDTO requestDTO = DTOFactory.createValidUserRequestDTO();
        requestDTO.setEmail("novoemail@example.com");
        requestDTO.getUserData().setCpfCnpj("12345678900");

        User existingUser = ModelFactory.createUser();
        existingUser.setEmail("usuarioexistente@example.com");
        existingUser.getUserData().setCpfCnpj("09876543211"); // CPF diferente do requestDTO

        when(userGateway.findByEmailOrUserData_CpfCnpj(anyString(), eq(requestDTO.getUserData().getCpfCnpj())))
                .thenReturn(Optional.of(existingUser));

        when(roleGateway.findByRole(anyString())).thenReturn(Optional.of(ModelFactory.createRole()));

        when(userGateway.save(any(User.class))).thenReturn(ModelFactory.createUser());

        assertDoesNotThrow(() -> userUseCase.register(requestDTO));
    }



}

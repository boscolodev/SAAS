package com.gbs.msauthentication.gateways;

import com.gbs.msauthentication.model.User;
import com.gbs.msauthentication.model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGatewayTest {

    @Mock
    private UserGateway userGateway;

    @Test
    void testFindByEmail() {
        User user = new User("john.doe@example.com", "password", new HashSet<>(), "ACTIVE", new UserData(), new ArrayList<>());
        when(userGateway.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userGateway.findByEmail("john.doe@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindByEmailOrUserData_CpfCnpj() {
        User user = new User("john.doe@example.com", "password", new HashSet<>(), "ACTIVE", new UserData("John", "Doe", "12345678901", "MG1234567", "01-01-1990", "123456789"), new ArrayList<>());
        when(userGateway.findByEmailOrUserData_CpfCnpj("john.doe@example.com", "12345678901")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userGateway.findByEmailOrUserData_CpfCnpj("john.doe@example.com", "12345678901");
        assertTrue(foundUser.isPresent());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
        assertEquals("12345678901", foundUser.get().getUserData().getCpfCnpj());
    }
}
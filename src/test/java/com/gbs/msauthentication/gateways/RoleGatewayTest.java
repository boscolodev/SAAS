package com.gbs.msauthentication.gateways;

import com.gbs.msauthentication.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleGatewayTest {

    @Mock
    private RoleGateway roleGateway;

    @Test
    void testFindByRole() {
        Role role = new Role(1L, "ROLE_USER", new HashSet<>());
        when(roleGateway.findByRole("ROLE_USER")).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleGateway.findByRole("ROLE_USER");
        assertTrue(foundRole.isPresent());
        assertEquals("ROLE_USER", foundRole.get().getRole());
    }
}
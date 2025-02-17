package com.gbs.msauthentication.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testGetAuthority() {
        Role role = new Role();
        role.setRole("ROLE_USER");
        assertEquals("ROLE_USER", role.getAuthority());
    }

    @Test
    void testRoleConstructor() {
        Role role = new Role(1L, "ROLE_ADMIN", new HashSet<>());
        assertEquals(1L, role.getId());
        assertEquals("ROLE_ADMIN", role.getRole());
    }

    @Test
    void testSettersAndGetters() {
        Role role = new Role();
        role.setId(2L);
        role.setRole("ROLE_MANAGER");

        assertEquals(2L, role.getId());
        assertEquals("ROLE_MANAGER", role.getRole());
    }
}
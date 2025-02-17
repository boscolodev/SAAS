package com.gbs.msauthentication.model;

import com.gbs.msauthentication.ModelFactory;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserConstructor() {
        UserData userData = ModelFactory.createUserData();
        Set<Role> roles = new HashSet<>();
        roles.add(ModelFactory.createRole());
        List<Address> addresses = new ArrayList<>();
        addresses.add(ModelFactory.createAddress());

        User user = new User("john.doe@example.com", "password", roles, "ACTIVE", userData, addresses);

        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(roles, user.getRoles());
        assertEquals("ACTIVE", user.getStatus());
        assertEquals(userData, user.getUserData());
        assertEquals(addresses, user.getAddresses());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setEmail("jane.smith@example.com");
        user.setPassword("password123");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "ROLE_ADMIN", new HashSet<>()));
        user.setRoles(roles);
        user.setStatus("INACTIVE");
        UserData userData = new UserData("Jane", "Smith", "10987654321", "SP7654321", "02-02-1992", "987654321");
        user.setUserData(userData);
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("2", "87654-321", "Rua B", "321", "Apto 2", "Bairro C", "Cidade D", "UF2", "Estado F", "Região S", "7654321", "GIA2", "22", "SIAFI2"));
        user.setAddresses(addresses);

        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(roles, user.getRoles());
        assertEquals("INACTIVE", user.getStatus());
        assertEquals(userData, user.getUserData());
        assertEquals(addresses, user.getAddresses());
    }

    @Test
    void testUserDetailsMethods() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "ROLE_USER", new HashSet<>()));
        user.setRoles(roles);

        assertEquals("user@example.com", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
    }
}
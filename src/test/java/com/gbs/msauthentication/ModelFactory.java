package com.gbs.msauthentication;

import com.gbs.msauthentication.model.Address;
import com.gbs.msauthentication.model.Role;
import com.gbs.msauthentication.model.UserData;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class ModelFactory {

    public static UserDetails createUserDetails(String username) {
        return User.withUsername(username)
                .password("password")
                .authorities(Collections.emptyList())
                .build();
    }

    public static UserDetails createUserDetailsWithAuthorities(String username, String... authorities) {
        return User.withUsername(username)
                .password("password")
                .authorities(authorities)
                .build();
    }

    public static Address createAddress() {
        return new Address(
                "1",
                "13466-000",
                "Rua Dom Pedro II",
                "1220",
                "Sala 2",
                "Nova Americana",
                "Americana",
                "SP",
                "São Paulo",
                "Sudeste",
                "1234567",
                "GIA",
                "11",
                "SIAFI"
        );
    }

    public static Role createRole() {
        return new Role(
                1L,
                "ROLE_USER",
                new HashSet<>()
        );
    }

    public static UserData createUserData() {
        return new UserData(
                "Marcos",
                "Freitas",
                "12345678979",
                "123456789",
                "12-05-1984",
                "1187645200"
        );
    }

    public static com.gbs.msauthentication.model.User createUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(createRole());
        List<Address> addresses = new ArrayList<>();
        addresses.add(createAddress());

        return new com.gbs.msauthentication.model.User(
                "user@example.com",
                "123456",
                roles,
                "ACTIVE",
                createUserData(),
                addresses
        );
    }

}


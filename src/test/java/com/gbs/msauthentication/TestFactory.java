package com.gbs.msauthentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class TestFactory {

    /**
     * Cria um UserDetails mockado com nome de usuário e autoridades específicas.
     *
     * @param username Nome de usuário
     * @return UserDetails configurado
     */
    public static UserDetails createUserDetails(String username) {
        return User.withUsername(username)
                .password("password") // Senha fictícia (não usada nos testes)
                .authorities(Collections.emptyList()) // Sem autoridades
                .build();
    }

    /**
     * Cria um UserDetails mockado com autoridades personalizadas.
     *
     * @param username   Nome de usuário
     * @param authorities Lista de autoridades
     * @return UserDetails configurado
     */
    public static UserDetails createUserDetailsWithAuthorities(String username, String... authorities) {
        return User.withUsername(username)
                .password("password")
                .authorities(authorities)
                .build();
    }
}

package com.gbs.msauthentication.usecase;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsername(final String email);
}

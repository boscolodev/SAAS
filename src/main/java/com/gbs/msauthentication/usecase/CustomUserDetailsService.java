package com.gbs.msauthentication.usecase;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService{
    UserDetails loadUserByUsername(final String email);
}

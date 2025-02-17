package com.gbs.msauthentication.security;

import com.gbs.msauthentication.gateways.UserGateway;
import com.gbs.msauthentication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationConfigTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationConfig = new ApplicationConfig(userGateway);
    }

    @Test
    void testUserDetailsService() {
        String username = "user@example.com";
        User user = mock(User.class);

        when(userGateway.findByEmail(username)).thenReturn(Optional.of(user));

        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        UserDetails loadedUser = userDetailsService.loadUserByUsername(username);

        assertEquals(user, loadedUser);
        verify(userGateway).findByEmail(username);
    }

    @Test
    void testUserDetailsServiceUserNotFound() {
        String username = "user@example.com";

        when(userGateway.findByEmail(username)).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
        verify(userGateway).findByEmail(username);
    }

    @Test
    void testAuthenticationProvider() throws Exception {
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        // Create a real instance of ApplicationConfig
        ApplicationConfig applicationConfig = new ApplicationConfig(userGateway);

        // Mock the methods to return the correct mocks
        ApplicationConfig applicationConfigSpy = spy(applicationConfig);
        doReturn(userDetailsService).when(applicationConfigSpy).userDetailsService();
        doReturn(passwordEncoder).when(applicationConfigSpy).passwordEncoder();

        DaoAuthenticationProvider authProvider = (DaoAuthenticationProvider) applicationConfigSpy.authenticationProvider();

        // Use reflection to access protected methods
        Method getUserDetailsServiceMethod = DaoAuthenticationProvider.class.getDeclaredMethod("getUserDetailsService");
        getUserDetailsServiceMethod.setAccessible(true);
        UserDetailsService actualUserDetailsService = (UserDetailsService) getUserDetailsServiceMethod.invoke(authProvider);

        Method getPasswordEncoderMethod = DaoAuthenticationProvider.class.getDeclaredMethod("getPasswordEncoder");
        getPasswordEncoderMethod.setAccessible(true);
        PasswordEncoder actualPasswordEncoder = (PasswordEncoder) getPasswordEncoderMethod.invoke(authProvider);

        assertEquals(userDetailsService, actualUserDetailsService);
        assertEquals(passwordEncoder, actualPasswordEncoder);
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = applicationConfig.authenticationManager(authenticationConfiguration);

        assertEquals(authenticationManager, result);
    }
}
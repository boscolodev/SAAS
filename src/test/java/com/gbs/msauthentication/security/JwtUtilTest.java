package com.gbs.msauthentication.security;

import com.gbs.msauthentication.ModelFactory;
import com.gbs.msauthentication.TestUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtUtilTest {

    @Mock
    private Key key;

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the key
        when(key.getEncoded()).thenReturn(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());

        // Set the mocked key in JwtUtil using reflection
        try {
            TestUtils.setPrivateField(jwtUtil, "key", key);
            TestUtils.setPrivateField(jwtUtil, "secret", "mySecretKey");
            TestUtils.setPrivateField(jwtUtil, "accessTokenExpiration", 3600L);
            TestUtils.setPrivateField(jwtUtil, "refreshTokenExpiration", 86400L);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao configurar JwtUtil para testes", e);
        }

        // Chama o método init() para inicializar a chave
        jwtUtil.init();
    }

    @Test
    void shouldGenerateTokenWhenUserDetailsWithUsernameAndPasswordIsCorrect() {
        UserDetails userDetails = ModelFactory.createUserDetails("admin@example.com");

        // Gera o token de acesso
        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void shouldValidateJwtStructureWhenTokenIsValid() {
        UserDetails userDetails = ModelFactory.createUserDetails("admin@example.com");

        // Gera o token de acesso
        String token = jwtUtil.generateToken(userDetails);

        // Verifica se a estrutura do token é válida
        assertTrue(jwtUtil.isValid(token));

        // Verifica se um token inválido retorna false
        assertFalse(jwtUtil.isValid("invalidToken"));
    }

    @Test
    void shouldExtractUsernameFromToken() {
        UserDetails userDetails = ModelFactory.createUserDetails("admin@example.com");

        // Gera o token de acesso
        String token = jwtUtil.generateToken(userDetails);

        // Extrai o username do token
        String username = jwtUtil.extractUsername(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void shouldGenerateRefreshToken() {
        UserDetails userDetails = ModelFactory.createUserDetails("user@example.com");

        // Gera o token de refresh
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        assertNotNull(refreshToken);
        assertTrue(refreshToken.startsWith("eyJ"));
    }

    @Test
    void shouldContainAuthoritiesInToken() {
        UserDetails userDetails = ModelFactory.createUserDetailsWithAuthorities("admin@example.com", "ROLE_USER", "ROLE_ADMIN");

        String token = jwtUtil.generateToken(userDetails);

       key = getPrivateField(jwtUtil, "key");

        List authorities = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("authorities", List.class);

        assertNotNull(key, "Key should not be null");
        assertNotNull(authorities, "Authorities should not be null");
        assertFalse(authorities.isEmpty(), "Authorities should not be empty");
        assertEquals(userDetails.getAuthorities().size(), authorities.size(), "Authorities size should match");
    }

    private Key getPrivateField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (Key) field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar o campo privado", e);
        }
    }
}
package com.example.Education_Java4b.config.jwt;

import com.example.Education_Java4b.models.Role;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private final JwtKeyProvider jwtKeyProvider = Mockito.mock(JwtKeyProvider.class);
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtKeyProvider);

    @Test
    void generateTokenTest() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Mockito.when(jwtKeyProvider.getJwtSecretKey()).thenReturn(secretKey);

        String username = "testUser";
        Role role = Role.NEW_USER;
        String token = jwtTokenProvider.generateToken(username, role);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromTokenTest() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Mockito.when(jwtKeyProvider.getJwtSecretKey()).thenReturn(secretKey);

        String username = "testUser";
        Role role = Role.NEW_USER;
        String token = jwtTokenProvider.generateToken(username, role);
        String decodedUsername = jwtTokenProvider.getUsernameFromToken(token);
        assertEquals(username, decodedUsername);
    }

    @Test
    void getRoleFromTokenTest() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Mockito.when(jwtKeyProvider.getJwtSecretKey()).thenReturn(secretKey);

        String username = "testUser";
        Role role = Role.NEW_USER;
        String token = jwtTokenProvider.generateToken(username, role);
        Role decodedRole = jwtTokenProvider.getRoleFromToken(token);
        assertEquals(role, decodedRole);
    }
}
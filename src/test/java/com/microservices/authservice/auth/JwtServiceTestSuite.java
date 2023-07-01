package com.microservices.authservice.auth;

import com.microservices.authservice.jwtAuthoritation.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTestSuite {

    @Autowired
    private JwtService jwtService;

    private Authentication auth;

    @BeforeEach
    void setUp() {
        auth = new Authentication();
        auth.setUsername("username");
        auth.setPassword("password");
        auth.setAuthority(Role.ADMIN);
        auth.setActive(true);
        auth.setUserId(1L);
    }

    @Test
    void extractUsernameTest() {
        // Given
        String token = generateToken();
        // When
        String username = jwtService.extractUsername(token);
        // Then
        assertEquals("username", username);
    }

    @Test
    void generateTokenWithExtraClaimsTest() {
        // Given
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("claim1", "value1");
        // When
        String token = jwtService.generateToken(extraClaims, auth);
        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
        String username = jwtService.extractUsername(token);
        assertEquals("username", username);
        assertEquals("value1", jwtService.extractClaim(token, claims -> claims.get("claim1", String.class)));
    }

    @Test
    void isTokenValidTest() {
        // Given
        String token = generateToken();
        // When
        boolean isValid = jwtService.isTokenValid(token, auth);
        // Then
        assertTrue(isValid);
        assertDoesNotThrow(() -> jwtService.isTokenValid(token));
    }

    private String generateToken() {
        return jwtService.generateToken(auth);
    }
}

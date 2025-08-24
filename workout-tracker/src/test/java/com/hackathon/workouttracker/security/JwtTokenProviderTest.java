package com.hackathon.workouttracker.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        // Set required values through reflection
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", "testSecretKeyWithAtLeast32CharactersForHS256Algorithm");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", 3600000L); // 1 hour
        
        // Create test user
        userDetails = new User("testuser", "password", new ArrayList<>());
    }

    @Test
    void generateToken_shouldCreateValidToken() {
        // When
        String token = jwtTokenProvider.generateToken(userDetails);
        
        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void validateToken_shouldReturnTrue_forValidToken() {
        // Given
        String token = jwtTokenProvider.generateToken(userDetails);
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(token, userDetails);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    void getUsernameFromJWT_shouldExtractCorrectUsername() {
        // Given
        String token = jwtTokenProvider.generateToken(userDetails);
        
        // When
        String extractedUsername = jwtTokenProvider.getUsernameFromJWT(token);
        
        // Then
        assertEquals("testuser", extractedUsername);
    }
}


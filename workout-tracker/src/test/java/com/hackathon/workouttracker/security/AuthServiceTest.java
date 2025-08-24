package com.hackathon.workouttracker.security;

import com.hackathon.workouttracker.security.dto.LoginRequest;
import com.hackathon.workouttracker.security.dto.SignupRequest;
import com.hackathon.workouttracker.user.UserEntity;
import com.hackathon.workouttracker.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private UserDetails userDetails;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("testuser", "password");
        signupRequest = new SignupRequest("testuser", "password");
        userDetails = new User("testuser", "encodedPassword", new ArrayList<>());
        userEntity = new UserEntity("testuser", "encodedPassword");
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        // Given
        String expectedToken = "valid.jwt.token";
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtTokenProvider.generateToken(userDetails)).thenReturn(expectedToken);

        // When
        String actualToken = authService.login(loginRequest);

        // Then
        assertEquals(expectedToken, actualToken);
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("testuser", "password"));
    }

    @Test
    void login_shouldThrowException_whenCredentialsAreInvalid() {
        // Given
        doThrow(new BadCredentialsException("Bad credentials"))  // Use concrete exception class instead of AuthenticationException
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authService.login(loginRequest));
        assertEquals("Login Failed. Please try again.", exception.getMessage());
    }

    @Test
    void signup_shouldSaveUser_whenUsernameDoesNotExist() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // When
        authService.signup(signupRequest);

        // Then
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void signup_shouldThrowException_whenUsernameExists() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authService.signup(signupRequest));
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}

package com.hackathon.workouttracker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.workouttracker.security.dto.LoginRequest;
import com.hackathon.workouttracker.security.dto.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController) // Use standalone setup instead of webAppContextSetup
                .build();
        objectMapper = new ObjectMapper();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void signup_shouldReturnSuccess_whenUserIsNew() throws Exception {
        SignupRequest signupRequest = new SignupRequest("testuser", "password");
        doNothing().when(authService).signup(any(SignupRequest.class));

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup Successful"));
    }

    @Test
    void signup_shouldReturnBadRequest_whenUserExists() throws Exception {
        SignupRequest signupRequest = new SignupRequest("existinguser", "password");
        doThrow(new IllegalArgumentException("User already registered. Please login."))
                .when(authService).signup(any(SignupRequest.class));

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already registered. Please login."));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        String fakeToken = "fake.jwt.token";
        when(authService.login(any(LoginRequest.class))).thenReturn(fakeToken);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(fakeToken));
    }

    @Test
    void login_shouldReturnBadRequest_whenCredentialsAreInvalid() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");
        doThrow(new IllegalArgumentException("Login Failed. Please try again."))
                .when(authService).login(any(LoginRequest.class));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Login Failed. Please try again."));
    }
}

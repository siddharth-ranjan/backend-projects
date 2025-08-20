package com.hackathon.workouttracker.security;

import com.hackathon.workouttracker.security.dto.LoginRequest;
import com.hackathon.workouttracker.security.dto.SignupRequest;
import com.hackathon.workouttracker.user.UserEntity;
import com.hackathon.workouttracker.user.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            return jwtTokenProvider.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Login Failed. Please try again.");
        }
    }

    public void signup(SignupRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity userEntity = new UserEntity(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("Data integrity violation");
        }
    }
}

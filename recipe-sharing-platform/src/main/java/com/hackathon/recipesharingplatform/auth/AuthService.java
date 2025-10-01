package com.hackathon.recipesharingplatform.auth;

import com.hackathon.recipesharingplatform.auth.dto.AuthRequest;
import com.hackathon.recipesharingplatform.auth.dto.AuthResponse;
import com.hackathon.recipesharingplatform.auth.dto.RegisterRequest;
import com.hackathon.recipesharingplatform.auth.model.Chef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AuthService {

    private final ChefRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(ChefRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        Chef chef = Chef.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(chef);
        log.info("User {} registered successfully", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        log.info("Attempting login for user: {}", request.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Chef chef = (Chef) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(chef);
        log.info("Login successful for user: {}", request.getUsername());

        return ResponseEntity.ok(new AuthResponse(accessToken));
    }
}

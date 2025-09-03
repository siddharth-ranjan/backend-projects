package com.hackathon.realtimechatapplication.shared.security.service;

import com.hackathon.realtimechatapplication.shared.security.JwtUtil;
import com.hackathon.realtimechatapplication.shared.security.dto.AuthRequest;
import com.hackathon.realtimechatapplication.shared.security.dto.AuthResponse;
import com.hackathon.realtimechatapplication.user.User;
import com.hackathon.realtimechatapplication.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> register(AuthRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already registered");
        }

        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().body("Successfully registered. Please Login!");
    }

    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "User is already registered."));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(true,  accessToken));
    }
}

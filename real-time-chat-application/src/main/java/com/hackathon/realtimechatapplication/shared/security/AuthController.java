package com.hackathon.realtimechatapplication.shared.security;

import com.hackathon.realtimechatapplication.shared.security.dto.AuthRequest;
import com.hackathon.realtimechatapplication.shared.security.dto.AuthResponse;
import com.hackathon.realtimechatapplication.shared.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    };

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}

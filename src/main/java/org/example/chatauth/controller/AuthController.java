package org.example.chatauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.AuthResponse;
import org.example.chatauth.dto.LoginRequest;
import org.example.chatauth.dto.RegisterRequest;
import org.example.chatauth.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthResponse register(
        @RequestBody RegisterRequest request) {

        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
        @RequestBody LoginRequest request) {
        return service.login(request);
    }
}

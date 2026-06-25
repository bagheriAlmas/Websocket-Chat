package org.example.chatauth.service;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.AuthResponse;
import org.example.chatauth.dto.LoginRequest;
import org.example.chatauth.dto.RegisterRequest;
import org.example.chatauth.entity.User;
import org.example.chatauth.jwt.JwtService;
import org.example.chatauth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request) {

        final var user = User.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role("USER")
            .build();

        repository.save(user);

        final var token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        final var token = jwtService.generateToken(request.email());

        return new AuthResponse(token);
    }
}

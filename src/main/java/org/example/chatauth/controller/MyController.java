package org.example.chatauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.AuthResponse;
import org.example.chatauth.dto.LoginRequest;
import org.example.chatauth.dto.RegisterRequest;
import org.example.chatauth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class MyController {

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var email = authentication.getName();
        return ResponseEntity.ok("Hello " + email);
    }
}

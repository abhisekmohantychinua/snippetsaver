package dev.abhisek.authservice.controller;

import dev.abhisek.authservice.model.AuthRequest;
import dev.abhisek.authservice.model.AuthResponse;
import dev.abhisek.authservice.model.RegisterRequest;
import dev.abhisek.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity
                .ok(authService
                        .register(request));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> token(@RequestBody AuthRequest request) {
        return ResponseEntity
                .ok(authService
                        .authorize(request));
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validate(@PathVariable String token) {
        System.out.println(token);
        return ResponseEntity
                .ok(authService.validateToken(token));
    }

}

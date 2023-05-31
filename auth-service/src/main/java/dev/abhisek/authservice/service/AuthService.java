package dev.abhisek.authservice.service;

import dev.abhisek.authservice.model.AuthRequest;
import dev.abhisek.authservice.model.AuthResponse;
import dev.abhisek.authservice.model.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse authorize(AuthRequest authRequest);
    Boolean validateToken(String token);

}

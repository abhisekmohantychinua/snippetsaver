package dev.abhisek.authservice.service.impl;

import dev.abhisek.authservice.entity.User;
import dev.abhisek.authservice.model.AuthRequest;
import dev.abhisek.authservice.model.AuthResponse;
import dev.abhisek.authservice.model.RegisterRequest;
import dev.abhisek.authservice.repository.UserRepository;
import dev.abhisek.authservice.service.AuthService;
import dev.abhisek.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        User user = User
                .builder()
                .userId(UUID
                        .randomUUID()
                        .toString())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder
                        .encode(registerRequest
                                .getPassword()))
                .roles(List.of("USER"))
                .build();
        repository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .token(jwt)
                .build();
    }

    @Override
    public AuthResponse authorize(AuthRequest authRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    ));
        } catch (BadCredentialsException | LockedException | DisabledException exception) {
            throw new UsernameNotFoundException("Requested user not found on server");
        }

        User user = repository.findByEmail(authRequest.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return AuthResponse
                .builder()
                .token(jwt)
                .build();
    }

    @Override
    public Boolean validateToken(String token) {
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.isTokenValid(token, userDetails);
    }


}

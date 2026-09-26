package com.example.studyprogress.controller;

import com.example.studyprogress.dto.AuthResponse;
import com.example.studyprogress.dto.LoginRequest;
import com.example.studyprogress.dto.RegistroRequest;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(
            @Valid @RequestBody RegistroRequest request
    ) {

        Usuario usuario =
                authService.registrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        String token =
                authService.login(request);

        AuthResponse response =
                new AuthResponse(
                        "Inicio de sesión correcto",
                        token
                );

        return ResponseEntity.ok(response);
    }
}
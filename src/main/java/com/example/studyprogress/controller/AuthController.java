package com.example.studyprogress.controller;

import com.example.studyprogress.dto.AuthResponse;
import com.example.studyprogress.dto.LoginRequest;
import com.example.studyprogress.dto.RegistroRequest;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.AuthService;
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
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest request) {

        try {
            Usuario usuario = authService.registrar(request);
            return ResponseEntity.ok(usuario);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {

        String token = authService.login(request);

        if (token == null) {
            return ResponseEntity.status(401)
                    .body(
                            new AuthResponse(
                                    "Email o contraseña incorrectos"
                            )
                    );
        }

        return ResponseEntity.ok(
                new AuthResponse(
                        "Inicio de sesión correcto",
                        token
                )
        );
    }
}
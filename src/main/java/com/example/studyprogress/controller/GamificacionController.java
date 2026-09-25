package com.example.studyprogress.controller;

import com.example.studyprogress.dto.GamificacionResponse;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.GamificacionService;
import com.example.studyprogress.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/gamificacion")
public class GamificacionController {

    private final UsuarioService usuarioService;
    private final GamificacionService gamificacionService;

    public GamificacionController(
            UsuarioService usuarioService,
            GamificacionService gamificacionService
    ) {
        this.usuarioService = usuarioService;
        this.gamificacionService = gamificacionService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerGamificacion(
            Authentication authentication
    ) {

        Optional<Usuario> usuarioOptional =
                usuarioService.buscarPorEmail(
                        authentication.getName()
                );

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        GamificacionResponse response =
                new GamificacionResponse(
                        usuario.getPuntos(),
                        gamificacionService.obtenerNivel(usuario)
                );

        return ResponseEntity.ok(response);
    }
}
package com.example.studyprogress.controller;

import com.example.studyprogress.dto.ObjetivoRequest;
import com.example.studyprogress.model.Objetivo;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.GamificacionService;
import com.example.studyprogress.service.ObjetivoService;
import com.example.studyprogress.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objetivos")
public class ObjetivoController {

    private final ObjetivoService objetivoService;
    private final UsuarioService usuarioService;
    private final GamificacionService gamificacionService;

    public ObjetivoController(
            ObjetivoService objetivoService,
            UsuarioService usuarioService,
            GamificacionService gamificacionService
    ) {
        this.objetivoService = objetivoService;
        this.usuarioService = usuarioService;
        this.gamificacionService = gamificacionService;
    }

    @PostMapping
    public ResponseEntity<?> crearObjetivo(
            @RequestBody ObjetivoRequest request,
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

        Objetivo objetivo = new Objetivo(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFechaLimite(),
                usuario
        );

        return ResponseEntity.ok(
                objetivoService.guardarObjetivo(objetivo)
        );
    }

    @GetMapping
    public ResponseEntity<?> listarObjetivos(
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

        List<Objetivo> objetivos =
                objetivoService.listarObjetivosPorUsuario(
                        usuarioOptional.get()
                );

        return ResponseEntity.ok(objetivos);
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<?> completarObjetivo(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Objetivo> objetivoOptional =
                objetivoService.buscarPorId(id);

        if (objetivoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Objetivo objetivo = objetivoOptional.get();

        if (!objetivo.getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a este objetivo");
        }

        if (!objetivo.isCompletado()) {

            objetivo.setCompletado(true);

            objetivoService.guardarObjetivo(objetivo);

            gamificacionService.sumarPuntos(
                    objetivo.getUsuario(),
                    30
            );
        }

        return ResponseEntity.ok(objetivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarObjetivo(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Objetivo> objetivoOptional =
                objetivoService.buscarPorId(id);

        if (objetivoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Objetivo objetivo = objetivoOptional.get();

        if (!objetivo.getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes permiso para eliminar este objetivo");
        }

        objetivoService.eliminarObjetivo(id);

        return ResponseEntity.ok(
                "Objetivo eliminado correctamente"
        );
    }
}
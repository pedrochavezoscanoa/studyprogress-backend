package com.example.studyprogress.controller;

import com.example.studyprogress.dto.ProgresoCursoResponse;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.ProgresoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProgresoController {

    private final ProgresoService progresoService;
    private final CursoService cursoService;

    public ProgresoController(
            ProgresoService progresoService,
            CursoService cursoService
    ) {
        this.progresoService = progresoService;
        this.cursoService = cursoService;
    }

    @GetMapping("/cursos/{cursoId}/progreso")
    public ResponseEntity<?> obtenerProgresoCurso(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {

        Optional<Curso> cursoOptional =
                cursoService.buscarPorId(cursoId);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOptional.get();

        if (!curso.getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a este curso");
        }

        ProgresoCursoResponse progreso =
                progresoService.calcularProgresoCurso(curso);

        return ResponseEntity.ok(progreso);
    }
}
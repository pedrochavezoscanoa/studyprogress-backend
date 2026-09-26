package com.example.studyprogress.controller;

import com.example.studyprogress.dto.ProgresoCursoResponse;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.ProgresoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProgresoCursoResponse> obtenerProgreso(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        authentication.getName()
                );

        ProgresoCursoResponse progreso =
                progresoService.calcularProgresoCurso(
                        curso
                );

        return ResponseEntity.ok(
                progreso
        );
    }
}
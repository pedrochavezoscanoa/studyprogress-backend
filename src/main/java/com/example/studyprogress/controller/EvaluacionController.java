package com.example.studyprogress.controller;

import com.example.studyprogress.dto.CalificacionRequest;
import com.example.studyprogress.dto.EvaluacionRequest;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Evaluacion;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.EvaluacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;
    private final CursoService cursoService;

    public EvaluacionController(
            EvaluacionService evaluacionService,
            CursoService cursoService
    ) {
        this.evaluacionService = evaluacionService;
        this.cursoService = cursoService;
    }

    @PostMapping("/cursos/{cursoId}/evaluaciones")
    public ResponseEntity<?> crearEvaluacion(
            @PathVariable Long cursoId,
            @RequestBody EvaluacionRequest request,
            Authentication authentication
    ) {

        Optional<Curso> cursoOptional =
                cursoService.buscarPorId(cursoId);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOptional.get();

        if (!curso.getUsuario().getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a este curso");
        }

        Evaluacion evaluacion = new Evaluacion(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFecha(),
                request.getTipo(),
                request.getPonderacion(),
                curso
        );

        return ResponseEntity.ok(
                evaluacionService.guardarEvaluacion(evaluacion)
        );
    }

    @GetMapping("/cursos/{cursoId}/evaluaciones")
    public ResponseEntity<?> listarEvaluaciones(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {

        Optional<Curso> cursoOptional =
                cursoService.buscarPorId(cursoId);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOptional.get();

        if (!curso.getUsuario().getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a este curso");
        }

        List<Evaluacion> evaluaciones =
                evaluacionService.listarEvaluacionesPorCurso(curso);

        return ResponseEntity.ok(evaluaciones);
    }

    @PutMapping("/evaluaciones/{id}/calificacion")
    public ResponseEntity<?> registrarCalificacion(
            @PathVariable Long id,
            @RequestBody CalificacionRequest request,
            Authentication authentication
    ) {

        Optional<Evaluacion> evaluacionOptional =
                evaluacionService.buscarPorId(id);

        if (evaluacionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Evaluacion evaluacion = evaluacionOptional.get();

        if (!evaluacion.getCurso()
                .getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a esta evaluación");
        }

        evaluacion.setCalificacion(
                request.getCalificacion()
        );

        return ResponseEntity.ok(
                evaluacionService.guardarEvaluacion(evaluacion)
        );
    }

    @DeleteMapping("/evaluaciones/{id}")
    public ResponseEntity<?> eliminarEvaluacion(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Evaluacion> evaluacionOptional =
                evaluacionService.buscarPorId(id);

        if (evaluacionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Evaluacion evaluacion = evaluacionOptional.get();

        if (!evaluacion.getCurso()
                .getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes permiso para eliminar esta evaluación");
        }

        evaluacionService.eliminarEvaluacion(id);

        return ResponseEntity.ok(
                "Evaluación eliminada correctamente"
        );
    }
}
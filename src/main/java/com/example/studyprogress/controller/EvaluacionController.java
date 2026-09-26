package com.example.studyprogress.controller;

import com.example.studyprogress.dto.CalificacionRequest;
import com.example.studyprogress.dto.EvaluacionRequest;
import com.example.studyprogress.dto.EvaluacionResponseDTO;
import com.example.studyprogress.mapper.EvaluacionMapper;
import com.example.studyprogress.model.Evaluacion;
import com.example.studyprogress.service.EvaluacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;
    private final EvaluacionMapper evaluacionMapper;

    public EvaluacionController(
            EvaluacionService evaluacionService,
            EvaluacionMapper evaluacionMapper
    ) {
        this.evaluacionService = evaluacionService;
        this.evaluacionMapper = evaluacionMapper;
    }

    @PostMapping("/cursos/{cursoId}/evaluaciones")
    public ResponseEntity<EvaluacionResponseDTO> crearEvaluacion(
            @PathVariable Long cursoId,
            @Valid @RequestBody EvaluacionRequest request,
            Authentication authentication
    ) {

        Evaluacion evaluacion =
                evaluacionService.crearEvaluacion(
                        cursoId,
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        evaluacionMapper.toDTO(evaluacion)
                );
    }

    @GetMapping("/cursos/{cursoId}/evaluaciones")
    public ResponseEntity<List<EvaluacionResponseDTO>> listarEvaluaciones(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {

        List<EvaluacionResponseDTO> evaluaciones =
                evaluacionService
                        .listarEvaluacionesDelCurso(
                                cursoId,
                                authentication.getName()
                        )
                        .stream()
                        .map(evaluacionMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(evaluaciones);
    }

    @PutMapping("/evaluaciones/{id}/calificacion")
    public ResponseEntity<EvaluacionResponseDTO> registrarCalificacion(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionRequest request,
            Authentication authentication
    ) {

        Evaluacion evaluacion =
                evaluacionService.registrarCalificacion(
                        id,
                        request,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                evaluacionMapper.toDTO(evaluacion)
        );
    }

    @DeleteMapping("/evaluaciones/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(
            @PathVariable Long id,
            Authentication authentication
    ) {

        evaluacionService.eliminarEvaluacionDelUsuario(
                id,
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
package com.example.studyprogress.controller;

import com.example.studyprogress.dto.TareaRequest;
import com.example.studyprogress.dto.TareaResponseDTO;
import com.example.studyprogress.mapper.TareaMapper;
import com.example.studyprogress.model.Tarea;
import com.example.studyprogress.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TareaController {

    private final TareaService tareaService;
    private final TareaMapper tareaMapper;

    public TareaController(
            TareaService tareaService,
            TareaMapper tareaMapper
    ) {
        this.tareaService = tareaService;
        this.tareaMapper = tareaMapper;
    }

    @PostMapping("/cursos/{cursoId}/tareas")
    public ResponseEntity<TareaResponseDTO> crearTarea(
            @PathVariable Long cursoId,
            @Valid @RequestBody TareaRequest request,
            Authentication authentication
    ) {

        Tarea tarea =
                tareaService.crearTarea(
                        cursoId,
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        tareaMapper.toDTO(tarea)
                );
    }

    @GetMapping("/cursos/{cursoId}/tareas")
    public ResponseEntity<List<TareaResponseDTO>> listarTareas(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {

        List<TareaResponseDTO> tareas =
                tareaService
                        .listarTareasDelCurso(
                                cursoId,
                                authentication.getName()
                        )
                        .stream()
                        .map(tareaMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(tareas);
    }

    @PutMapping("/tareas/{id}/completar")
    public ResponseEntity<TareaResponseDTO> completarTarea(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Tarea tarea =
                tareaService.completarTarea(
                        id,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                tareaMapper.toDTO(tarea)
        );
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> eliminarTarea(
            @PathVariable Long id,
            Authentication authentication
    ) {

        tareaService.eliminarTareaDelUsuario(
                id,
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
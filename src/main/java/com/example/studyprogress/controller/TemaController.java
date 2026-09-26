package com.example.studyprogress.controller;

import com.example.studyprogress.dto.TemaRequest;
import com.example.studyprogress.dto.TemaResponseDTO;
import com.example.studyprogress.mapper.TemaMapper;
import com.example.studyprogress.model.Tema;
import com.example.studyprogress.service.TemaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TemaController {

    private final TemaService temaService;
    private final TemaMapper temaMapper;

    public TemaController(
            TemaService temaService,
            TemaMapper temaMapper
    ) {
        this.temaService = temaService;
        this.temaMapper = temaMapper;
    }

    @PostMapping("/cursos/{cursoId}/temas")
    public ResponseEntity<TemaResponseDTO> crearTema(
            @PathVariable Long cursoId,
            @Valid @RequestBody TemaRequest request,
            Authentication authentication
    ) {

        Tema tema =
                temaService.crearTema(
                        cursoId,
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        temaMapper.toDTO(tema)
                );
    }

    @GetMapping("/cursos/{cursoId}/temas")
    public ResponseEntity<List<TemaResponseDTO>> listarTemas(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {

        List<TemaResponseDTO> temas =
                temaService
                        .listarTemasDelCurso(
                                cursoId,
                                authentication.getName()
                        )
                        .stream()
                        .map(temaMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(temas);
    }

    @PutMapping("/temas/{id}/completar")
    public ResponseEntity<TemaResponseDTO> completarTema(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Tema tema =
                temaService.completarTema(
                        id,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                temaMapper.toDTO(tema)
        );
    }

    @DeleteMapping("/temas/{id}")
    public ResponseEntity<Void> eliminarTema(
            @PathVariable Long id,
            Authentication authentication
    ) {

        temaService.eliminarTemaDelUsuario(
                id,
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
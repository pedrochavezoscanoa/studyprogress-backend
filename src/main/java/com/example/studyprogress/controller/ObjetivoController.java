package com.example.studyprogress.controller;

import com.example.studyprogress.dto.ObjetivoRequest;
import com.example.studyprogress.dto.ObjetivoResponseDTO;
import com.example.studyprogress.mapper.ObjetivoMapper;
import com.example.studyprogress.model.Objetivo;
import com.example.studyprogress.service.ObjetivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objetivos")
public class ObjetivoController {

    private final ObjetivoService objetivoService;
    private final ObjetivoMapper objetivoMapper;

    public ObjetivoController(
            ObjetivoService objetivoService,
            ObjetivoMapper objetivoMapper
    ) {
        this.objetivoService = objetivoService;
        this.objetivoMapper = objetivoMapper;
    }

    @PostMapping
    public ResponseEntity<ObjetivoResponseDTO> crearObjetivo(
            @Valid @RequestBody ObjetivoRequest request,
            Authentication authentication
    ) {

        Objetivo objetivo =
                objetivoService.crearObjetivo(
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        objetivoMapper.toDTO(objetivo)
                );
    }

    @GetMapping
    public ResponseEntity<List<ObjetivoResponseDTO>> listarObjetivos(
            Authentication authentication
    ) {

        List<ObjetivoResponseDTO> objetivos =
                objetivoService
                        .listarObjetivosDelUsuario(
                                authentication.getName()
                        )
                        .stream()
                        .map(objetivoMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(objetivos);
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<ObjetivoResponseDTO> completarObjetivo(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Objetivo objetivo =
                objetivoService.completarObjetivo(
                        id,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                objetivoMapper.toDTO(objetivo)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarObjetivo(
            @PathVariable Long id,
            Authentication authentication
    ) {

        objetivoService.eliminarObjetivoDelUsuario(
                id,
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
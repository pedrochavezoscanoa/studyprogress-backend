package com.example.studyprogress.controller;

import com.example.studyprogress.dto.RecordatorioRequest;
import com.example.studyprogress.dto.RecordatorioResponseDTO;
import com.example.studyprogress.mapper.RecordatorioMapper;
import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.service.RecordatorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recordatorios")
public class RecordatorioController {

    private final RecordatorioService recordatorioService;
    private final RecordatorioMapper recordatorioMapper;

    public RecordatorioController(
            RecordatorioService recordatorioService,
            RecordatorioMapper recordatorioMapper
    ) {
        this.recordatorioService =
                recordatorioService;

        this.recordatorioMapper =
                recordatorioMapper;
    }

    @PostMapping
    public ResponseEntity<RecordatorioResponseDTO> crearRecordatorio(
            @Valid @RequestBody RecordatorioRequest request,
            Authentication authentication
    ) {

        Recordatorio recordatorio =
                recordatorioService.crearRecordatorio(
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        recordatorioMapper.toDTO(
                                recordatorio
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<RecordatorioResponseDTO>> listarRecordatorios(
            Authentication authentication
    ) {

        List<RecordatorioResponseDTO> recordatorios =
                recordatorioService
                        .listarRecordatoriosDelUsuario(
                                authentication.getName()
                        )
                        .stream()
                        .map(recordatorioMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(
                recordatorios
        );
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<RecordatorioResponseDTO>> listarPendientes(
            Authentication authentication
    ) {

        List<RecordatorioResponseDTO> recordatorios =
                recordatorioService
                        .listarPendientesDelUsuario(
                                authentication.getName()
                        )
                        .stream()
                        .map(recordatorioMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(
                recordatorios
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecordatorio(
            @PathVariable Long id,
            Authentication authentication
    ) {

        recordatorioService
                .eliminarRecordatorioDelUsuario(
                        id,
                        authentication.getName()
                );

        return ResponseEntity
                .noContent()
                .build();
    }
}
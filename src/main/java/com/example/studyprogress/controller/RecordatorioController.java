package com.example.studyprogress.controller;

import com.example.studyprogress.dto.RecordatorioRequest;
import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.RecordatorioService;
import com.example.studyprogress.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recordatorios")
public class RecordatorioController {

    private final RecordatorioService recordatorioService;
    private final UsuarioService usuarioService;

    public RecordatorioController(
            RecordatorioService recordatorioService,
            UsuarioService usuarioService
    ) {
        this.recordatorioService = recordatorioService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> crearRecordatorio(
            @RequestBody RecordatorioRequest request,
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

        Recordatorio recordatorio = new Recordatorio(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFechaHora(),
                usuario
        );

        return ResponseEntity.ok(
                recordatorioService.guardarRecordatorio(recordatorio)
        );
    }

    @GetMapping
    public ResponseEntity<?> listarRecordatorios(
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

        List<Recordatorio> recordatorios =
                recordatorioService
                        .listarRecordatoriosPorUsuario(
                                usuarioOptional.get()
                        );

        return ResponseEntity.ok(recordatorios);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<?> listarPendientes(
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

        List<Recordatorio> pendientes =
                recordatorioService
                        .listarPendientesPorUsuario(
                                usuarioOptional.get()
                        );

        return ResponseEntity.ok(pendientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRecordatorio(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Recordatorio> recordatorioOptional =
                recordatorioService.buscarPorId(id);

        if (recordatorioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Recordatorio recordatorio =
                recordatorioOptional.get();

        if (!recordatorio.getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes permiso para eliminar este recordatorio");
        }

        recordatorioService.eliminarRecordatorio(id);

        return ResponseEntity.ok(
                "Recordatorio eliminado correctamente"
        );
    }
}
package com.example.studyprogress.controller;

import com.example.studyprogress.dto.UsuarioResponseDTO;
import com.example.studyprogress.mapper.UsuarioMapper;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(
            UsuarioService usuarioService,
            UsuarioMapper usuarioMapper
    ) {
        this.usuarioService =
                usuarioService;

        this.usuarioMapper =
                usuarioMapper;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

        List<UsuarioResponseDTO> usuarios =
                usuarioService
                        .listarUsuarios()
                        .stream()
                        .map(usuarioMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(
                usuarios
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(
            @PathVariable Long id
    ) {

        Usuario usuario =
                usuarioService.obtenerUsuarioPorId(
                        id
                );

        return ResponseEntity.ok(
                usuarioMapper.toDTO(
                        usuario
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Long id
    ) {

        usuarioService.eliminarUsuarioPorId(
                id
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
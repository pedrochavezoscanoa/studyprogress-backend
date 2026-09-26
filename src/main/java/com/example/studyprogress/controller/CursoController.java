package com.example.studyprogress.controller;

import com.example.studyprogress.dto.CursoRequest;
import com.example.studyprogress.dto.CursoResponseDTO;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.mapper.CursoMapper;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final UsuarioService usuarioService;
    private final CursoMapper cursoMapper;

    public CursoController(
            CursoService cursoService,
            UsuarioService usuarioService,
            CursoMapper cursoMapper
    ) {
        this.cursoService = cursoService;
        this.usuarioService = usuarioService;
        this.cursoMapper = cursoMapper;
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> crearCurso(
            @Valid @RequestBody CursoRequest request,
            Authentication authentication
    ) {

        Usuario usuario =
                usuarioService.buscarPorEmail(
                        authentication.getName()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        Curso curso = new Curso(
                request.getNombre(),
                request.getDescripcion(),
                usuario
        );

        Curso nuevoCurso =
                cursoService.guardarCurso(curso);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        cursoMapper.toDTO(nuevoCurso)
                );
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos(
            Authentication authentication
    ) {

        Usuario usuario =
                usuarioService.buscarPorEmail(
                        authentication.getName()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        List<CursoResponseDTO> cursos =
                cursoService
                        .listarCursosPorUsuario(usuario)
                        .stream()
                        .map(cursoMapper::toDTO)
                        .toList();

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> buscarCurso(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        id,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                cursoMapper.toDTO(curso)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(
            @PathVariable Long id,
            Authentication authentication
    ) {

        cursoService.eliminarCursoDelUsuario(
                id,
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
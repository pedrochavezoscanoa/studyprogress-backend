package com.example.studyprogress.controller;

import com.example.studyprogress.dto.CursoRequest;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final UsuarioService usuarioService;

    public CursoController(
            CursoService cursoService,
            UsuarioService usuarioService
    ) {
        this.cursoService = cursoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> crearCurso(
            @RequestBody CursoRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        Optional<Usuario> usuarioOptional =
                usuarioService.buscarPorEmail(email);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        Curso curso = new Curso(
                request.getNombre(),
                request.getDescripcion(),
                usuario
        );

        Curso nuevoCurso = cursoService.guardarCurso(curso);

        return ResponseEntity.ok(nuevoCurso);
    }

    @GetMapping
    public ResponseEntity<?> listarCursos(
            Authentication authentication
    ) {

        String email = authentication.getName();

        Optional<Usuario> usuarioOptional =
                usuarioService.buscarPorEmail(email);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Usuario no encontrado");
        }

        List<Curso> cursos =
                cursoService.listarCursosPorUsuario(
                        usuarioOptional.get()
                );

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCurso(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Curso> cursoOptional =
                cursoService.buscarPorId(id);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOptional.get();

        if (!curso.getUsuario().getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a este curso");
        }

        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Curso> cursoOptional =
                cursoService.buscarPorId(id);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOptional.get();

        if (!curso.getUsuario().getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes permiso para eliminar este curso");
        }

        cursoService.eliminarCurso(id);

        return ResponseEntity.ok(
                "Curso eliminado correctamente"
        );
    }
}
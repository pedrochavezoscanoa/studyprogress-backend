package com.example.studyprogress.controller;

import com.example.studyprogress.dto.TemaRequest;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tema;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.GamificacionService;
import com.example.studyprogress.service.TemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TemaController {

    private final TemaService temaService;
    private final CursoService cursoService;
    private final GamificacionService gamificacionService;

    public TemaController(
            TemaService temaService,
            CursoService cursoService,
            GamificacionService gamificacionService
    ) {
        this.temaService = temaService;
        this.cursoService = cursoService;
        this.gamificacionService = gamificacionService;
    }

    @PostMapping("/cursos/{cursoId}/temas")
    public ResponseEntity<?> crearTema(
            @PathVariable Long cursoId,
            @RequestBody TemaRequest request,
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

        Tema tema = new Tema(
                request.getNombre(),
                request.getDescripcion(),
                curso
        );

        return ResponseEntity.ok(
                temaService.guardarTema(tema)
        );
    }

    @GetMapping("/cursos/{cursoId}/temas")
    public ResponseEntity<?> listarTemas(
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

        List<Tema> temas =
                temaService.listarTemasPorCurso(curso);

        return ResponseEntity.ok(temas);
    }

    @PutMapping("/temas/{id}/completar")
    public ResponseEntity<?> completarTema(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Tema> temaOptional =
                temaService.buscarPorId(id);

        if (temaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tema tema = temaOptional.get();

        if (!tema.getCurso()
                .getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a este tema");
        }

        if (!tema.isCompletado()) {

            tema.setCompletado(true);

            temaService.guardarTema(tema);

            Usuario usuario =
                    tema.getCurso().getUsuario();

            gamificacionService.sumarPuntos(
                    usuario,
                    10
            );
        }

        return ResponseEntity.ok(tema);
    }

    @DeleteMapping("/temas/{id}")
    public ResponseEntity<?> eliminarTema(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Tema> temaOptional =
                temaService.buscarPorId(id);

        if (temaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tema tema = temaOptional.get();

        if (!tema.getCurso()
                .getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes permiso para eliminar este tema");
        }

        temaService.eliminarTema(id);

        return ResponseEntity.ok(
                "Tema eliminado correctamente"
        );
    }
}
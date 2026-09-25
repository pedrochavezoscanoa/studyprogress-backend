package com.example.studyprogress.controller;

import com.example.studyprogress.dto.TareaRequest;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tarea;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.service.CursoService;
import com.example.studyprogress.service.GamificacionService;
import com.example.studyprogress.service.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TareaController {

    private final TareaService tareaService;
    private final CursoService cursoService;
    private final GamificacionService gamificacionService;

    public TareaController(
            TareaService tareaService,
            CursoService cursoService,
            GamificacionService gamificacionService
    ) {
        this.tareaService = tareaService;
        this.cursoService = cursoService;
        this.gamificacionService = gamificacionService;
    }

    @PostMapping("/cursos/{cursoId}/tareas")
    public ResponseEntity<?> crearTarea(
            @PathVariable Long cursoId,
            @RequestBody TareaRequest request,
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

        Tarea tarea = new Tarea(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFechaLimite(),
                request.getPrioridad(),
                curso
        );

        return ResponseEntity.ok(
                tareaService.guardarTarea(tarea)
        );
    }

    @GetMapping("/cursos/{cursoId}/tareas")
    public ResponseEntity<?> listarTareas(
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

        List<Tarea> tareas =
                tareaService.listarTareasPorCurso(curso);

        return ResponseEntity.ok(tareas);
    }

    @PutMapping("/tareas/{id}/completar")
    public ResponseEntity<?> completarTarea(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Tarea> tareaOptional =
                tareaService.buscarPorId(id);

        if (tareaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tarea tarea = tareaOptional.get();

        if (!tarea.getCurso()
                .getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes acceso a esta tarea");
        }

        if (!tarea.isCompletada()) {

            tarea.setCompletada(true);

            tareaService.guardarTarea(tarea);

            Usuario usuario =
                    tarea.getCurso().getUsuario();

            gamificacionService.sumarPuntos(
                    usuario,
                    20
            );
        }

        return ResponseEntity.ok(tarea);
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<?> eliminarTarea(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Optional<Tarea> tareaOptional =
                tareaService.buscarPorId(id);

        if (tareaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tarea tarea = tareaOptional.get();

        if (!tarea.getCurso()
                .getUsuario()
                .getEmail()
                .equals(authentication.getName())) {

            return ResponseEntity.status(403)
                    .body("No tienes permiso para eliminar esta tarea");
        }

        tareaService.eliminarTarea(id);

        return ResponseEntity.ok(
                "Tarea eliminada correctamente"
        );
    }
}
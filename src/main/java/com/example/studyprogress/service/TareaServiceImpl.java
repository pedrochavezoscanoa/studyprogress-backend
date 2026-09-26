package com.example.studyprogress.service;

import com.example.studyprogress.dto.TareaRequest;
import com.example.studyprogress.exception.ForbiddenOperationException;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tarea;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final CursoService cursoService;
    private final GamificacionService gamificacionService;

    public TareaServiceImpl(
            TareaRepository tareaRepository,
            CursoService cursoService,
            GamificacionService gamificacionService
    ) {
        this.tareaRepository = tareaRepository;
        this.cursoService = cursoService;
        this.gamificacionService = gamificacionService;
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> listarTareasPorCurso(Curso curso) {
        return tareaRepository.findByCurso(curso);
    }

    @Override
    public List<Tarea> listarTareasPorEstado(
            Curso curso,
            boolean completada
    ) {
        return tareaRepository.findByCursoAndCompletada(
                curso,
                completada
        );
    }

    @Override
    public Optional<Tarea> buscarPorId(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public Tarea crearTarea(
            Long cursoId,
            TareaRequest request,
            String email
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        email
                );

        Tarea tarea = new Tarea(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFechaLimite(),
                request.getPrioridad(),
                curso
        );

        return tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> listarTareasDelCurso(
            Long cursoId,
            String email
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        email
                );

        return tareaRepository.findByCurso(curso);
    }

    @Override
    public Tarea completarTarea(
            Long id,
            String email
    ) {

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tarea no encontrada"
                        )
                );

        if (!tarea.getCurso()
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a esta tarea"
            );
        }

        if (!tarea.isCompletada()) {

            tarea.setCompletada(true);

            tareaRepository.save(tarea);

            Usuario usuario =
                    tarea.getCurso().getUsuario();

            gamificacionService.sumarPuntos(
                    usuario,
                    20
            );
        }

        return tarea;
    }

    @Override
    public void eliminarTareaDelUsuario(
            Long id,
            String email
    ) {

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tarea no encontrada"
                        )
                );

        if (!tarea.getCurso()
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes permiso para eliminar esta tarea"
            );
        }

        tareaRepository.delete(tarea);
    }
}
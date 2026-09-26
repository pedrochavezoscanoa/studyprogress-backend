package com.example.studyprogress.service;

import com.example.studyprogress.dto.TareaRequest;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {

    Tarea guardarTarea(Tarea tarea);

    List<Tarea> listarTareasPorCurso(Curso curso);

    List<Tarea> listarTareasPorEstado(
            Curso curso,
            boolean completada
    );

    Optional<Tarea> buscarPorId(Long id);

    void eliminarTarea(Long id);

    Tarea crearTarea(
            Long cursoId,
            TareaRequest request,
            String email
    );

    List<Tarea> listarTareasDelCurso(
            Long cursoId,
            String email
    );

    Tarea completarTarea(
            Long id,
            String email
    );

    void eliminarTareaDelUsuario(
            Long id,
            String email
    );
}
package com.example.studyprogress.service;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Evaluacion;

import java.util.List;
import java.util.Optional;

public interface EvaluacionService {

    Evaluacion guardarEvaluacion(Evaluacion evaluacion);

    List<Evaluacion> listarEvaluacionesPorCurso(Curso curso);

    Optional<Evaluacion> buscarPorId(Long id);

    void eliminarEvaluacion(Long id);
}
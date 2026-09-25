package com.example.studyprogress.service;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Evaluacion;
import com.example.studyprogress.repository.EvaluacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public EvaluacionServiceImpl(
            EvaluacionRepository evaluacionRepository
    ) {
        this.evaluacionRepository = evaluacionRepository;
    }

    @Override
    public Evaluacion guardarEvaluacion(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public List<Evaluacion> listarEvaluacionesPorCurso(Curso curso) {
        return evaluacionRepository.findByCurso(curso);
    }

    @Override
    public Optional<Evaluacion> buscarPorId(Long id) {
        return evaluacionRepository.findById(id);
    }

    @Override
    public void eliminarEvaluacion(Long id) {
        evaluacionRepository.deleteById(id);
    }
}
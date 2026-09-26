package com.example.studyprogress.service;

import com.example.studyprogress.dto.CalificacionRequest;
import com.example.studyprogress.dto.EvaluacionRequest;
import com.example.studyprogress.exception.ForbiddenOperationException;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Evaluacion;
import com.example.studyprogress.repository.EvaluacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final CursoService cursoService;

    public EvaluacionServiceImpl(
            EvaluacionRepository evaluacionRepository,
            CursoService cursoService
    ) {
        this.evaluacionRepository = evaluacionRepository;
        this.cursoService = cursoService;
    }

    @Override
    public Evaluacion guardarEvaluacion(
            Evaluacion evaluacion
    ) {
        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public List<Evaluacion> listarEvaluacionesPorCurso(
            Curso curso
    ) {
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

    @Override
    public Evaluacion crearEvaluacion(
            Long cursoId,
            EvaluacionRequest request,
            String email
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        email
                );

        Evaluacion evaluacion = new Evaluacion(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFecha(),
                request.getTipo(),
                request.getPonderacion(),
                curso
        );

        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public List<Evaluacion> listarEvaluacionesDelCurso(
            Long cursoId,
            String email
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        email
                );

        return evaluacionRepository.findByCurso(curso);
    }

    @Override
    public Evaluacion registrarCalificacion(
            Long id,
            CalificacionRequest request,
            String email
    ) {

        Evaluacion evaluacion =
                evaluacionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Evaluación no encontrada"
                                )
                        );

        if (!evaluacion.getCurso()
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a esta evaluación"
            );
        }

        evaluacion.setCalificacion(
                request.getCalificacion()
        );

        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public void eliminarEvaluacionDelUsuario(
            Long id,
            String email
    ) {

        Evaluacion evaluacion =
                evaluacionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Evaluación no encontrada"
                                )
                        );

        if (!evaluacion.getCurso()
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes permiso para eliminar esta evaluación"
            );
        }

        evaluacionRepository.delete(evaluacion);
    }
}
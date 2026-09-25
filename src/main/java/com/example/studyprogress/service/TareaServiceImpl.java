package com.example.studyprogress.service;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tarea;
import com.example.studyprogress.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;

    public TareaServiceImpl(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
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
}
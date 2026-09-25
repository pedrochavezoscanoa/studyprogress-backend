package com.example.studyprogress.service;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tema;
import com.example.studyprogress.repository.TemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaServiceImpl implements TemaService {

    private final TemaRepository temaRepository;

    public TemaServiceImpl(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    @Override
    public Tema guardarTema(Tema tema) {
        return temaRepository.save(tema);
    }

    @Override
    public List<Tema> listarTemasPorCurso(Curso curso) {
        return temaRepository.findByCurso(curso);
    }

    @Override
    public Optional<Tema> buscarPorId(Long id) {
        return temaRepository.findById(id);
    }

    @Override
    public void eliminarTema(Long id) {
        temaRepository.deleteById(id);
    }
}
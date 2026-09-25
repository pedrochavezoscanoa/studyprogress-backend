package com.example.studyprogress.service;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tema;

import java.util.List;
import java.util.Optional;

public interface TemaService {

    Tema guardarTema(Tema tema);

    List<Tema> listarTemasPorCurso(Curso curso);

    Optional<Tema> buscarPorId(Long id);

    void eliminarTema(Long id);
}
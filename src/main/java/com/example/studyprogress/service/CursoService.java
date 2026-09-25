package com.example.studyprogress.service;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    Curso guardarCurso(Curso curso);

    List<Curso> listarCursosPorUsuario(Usuario usuario);

    Optional<Curso> buscarPorId(Long id);

    void eliminarCurso(Long id);
}
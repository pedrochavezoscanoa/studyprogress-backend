package com.example.studyprogress.repository;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByUsuario(Usuario usuario);
}
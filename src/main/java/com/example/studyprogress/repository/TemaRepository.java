package com.example.studyprogress.repository;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Long> {

    List<Tema> findByCurso(Curso curso);
}
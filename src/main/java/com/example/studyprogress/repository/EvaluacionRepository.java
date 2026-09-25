package com.example.studyprogress.repository;

import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByCurso(Curso curso);
}
package com.example.studyprogress.service;

import com.example.studyprogress.dto.ProgresoCursoResponse;
import com.example.studyprogress.model.Curso;

public interface ProgresoService {

    ProgresoCursoResponse calcularProgresoCurso(Curso curso);
}
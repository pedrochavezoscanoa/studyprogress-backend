package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.EvaluacionResponseDTO;
import com.example.studyprogress.model.Evaluacion;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionMapper {

    public EvaluacionResponseDTO toDTO(Evaluacion evaluacion) {

        Long cursoId = null;

        if (evaluacion.getCurso() != null) {
            cursoId = evaluacion.getCurso().getId();
        }

        return new EvaluacionResponseDTO(
                evaluacion.getId(),
                evaluacion.getTitulo(),
                evaluacion.getDescripcion(),
                evaluacion.getFecha(),
                evaluacion.getTipo(),
                evaluacion.getCalificacion(),
                evaluacion.getPonderacion(),
                cursoId
        );
    }
}
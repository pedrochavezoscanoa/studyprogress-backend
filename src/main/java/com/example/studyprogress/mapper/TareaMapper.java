package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.TareaResponseDTO;
import com.example.studyprogress.model.Tarea;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {

    public TareaResponseDTO toDTO(Tarea tarea) {

        Long cursoId = null;

        if (tarea.getCurso() != null) {
            cursoId = tarea.getCurso().getId();
        }

        return new TareaResponseDTO(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaLimite(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                cursoId
        );
    }
}
package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.TemaResponseDTO;
import com.example.studyprogress.model.Tema;
import org.springframework.stereotype.Component;

@Component
public class TemaMapper {

    public TemaResponseDTO toDTO(Tema tema) {

        Long cursoId = null;

        if (tema.getCurso() != null) {
            cursoId = tema.getCurso().getId();
        }

        return new TemaResponseDTO(
                tema.getId(),
                tema.getNombre(),
                tema.getDescripcion(),
                tema.isCompletado(),
                cursoId
        );
    }
}
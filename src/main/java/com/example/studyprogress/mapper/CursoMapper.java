package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.CursoResponseDTO;
import com.example.studyprogress.model.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    public CursoResponseDTO toDTO(Curso curso) {

        return new CursoResponseDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getDescripcion()
        );
    }
}
package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.ObjetivoResponseDTO;
import com.example.studyprogress.model.Objetivo;
import org.springframework.stereotype.Component;

@Component
public class ObjetivoMapper {

    public ObjetivoResponseDTO toDTO(Objetivo objetivo) {

        return new ObjetivoResponseDTO(
                objetivo.getId(),
                objetivo.getTitulo(),
                objetivo.getDescripcion(),
                objetivo.getFechaLimite(),
                objetivo.isCompletado()
        );
    }
}
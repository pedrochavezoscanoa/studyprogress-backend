package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.RecordatorioResponseDTO;
import com.example.studyprogress.model.Recordatorio;
import org.springframework.stereotype.Component;

@Component
public class RecordatorioMapper {

    public RecordatorioResponseDTO toDTO(
            Recordatorio recordatorio
    ) {

        return new RecordatorioResponseDTO(
                recordatorio.getId(),
                recordatorio.getTitulo(),
                recordatorio.getDescripcion(),
                recordatorio.getFechaHora(),
                recordatorio.isEnviado()
        );
    }
}
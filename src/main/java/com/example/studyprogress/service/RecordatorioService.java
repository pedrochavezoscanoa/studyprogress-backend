package com.example.studyprogress.service;

import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordatorioService {

    Recordatorio guardarRecordatorio(Recordatorio recordatorio);

    List<Recordatorio> listarRecordatoriosPorUsuario(
            Usuario usuario
    );

    List<Recordatorio> listarPendientesPorUsuario(
            Usuario usuario
    );

    List<Recordatorio> listarPendientesParaEnviar(
            LocalDateTime fechaHora
    );

    Optional<Recordatorio> buscarPorId(Long id);

    void eliminarRecordatorio(Long id);
}
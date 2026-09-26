package com.example.studyprogress.service;

import com.example.studyprogress.dto.RecordatorioRequest;
import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordatorioService {

    Recordatorio guardarRecordatorio(
            Recordatorio recordatorio
    );

    List<Recordatorio> listarRecordatoriosPorUsuario(
            Usuario usuario
    );

    List<Recordatorio> listarPendientesPorUsuario(
            Usuario usuario
    );

    Optional<Recordatorio> buscarPorId(
            Long id
    );

    void eliminarRecordatorio(
            Long id
    );

    List<Recordatorio> listarRecordatoriosPendientesHasta(
            LocalDateTime fechaHora
    );

    Recordatorio crearRecordatorio(
            RecordatorioRequest request,
            String email
    );

    List<Recordatorio> listarRecordatoriosDelUsuario(
            String email
    );

    List<Recordatorio> listarPendientesDelUsuario(
            String email
    );

    void eliminarRecordatorioDelUsuario(
            Long id,
            String email
    );
}
package com.example.studyprogress.service;

import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.RecordatorioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordatorioServiceImpl implements RecordatorioService {

    private final RecordatorioRepository recordatorioRepository;

    public RecordatorioServiceImpl(
            RecordatorioRepository recordatorioRepository
    ) {
        this.recordatorioRepository = recordatorioRepository;
    }

    @Override
    public Recordatorio guardarRecordatorio(
            Recordatorio recordatorio
    ) {
        return recordatorioRepository.save(recordatorio);
    }

    @Override
    public List<Recordatorio> listarRecordatoriosPorUsuario(
            Usuario usuario
    ) {
        return recordatorioRepository.findByUsuario(usuario);
    }

    @Override
    public List<Recordatorio> listarPendientesPorUsuario(
            Usuario usuario
    ) {
        return recordatorioRepository
                .findByUsuarioAndEnviado(
                        usuario,
                        false
                );
    }

    @Override
    public List<Recordatorio> listarPendientesParaEnviar(
            LocalDateTime fechaHora
    ) {
        return recordatorioRepository
                .findByEnviadoFalseAndFechaHoraBefore(
                        fechaHora
                );
    }

    @Override
    public Optional<Recordatorio> buscarPorId(Long id) {
        return recordatorioRepository.findById(id);
    }

    @Override
    public void eliminarRecordatorio(Long id) {
        recordatorioRepository.deleteById(id);
    }
}
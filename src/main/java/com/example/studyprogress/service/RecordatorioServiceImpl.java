package com.example.studyprogress.service;

import com.example.studyprogress.dto.RecordatorioRequest;
import com.example.studyprogress.exception.ForbiddenOperationException;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.RecordatorioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordatorioServiceImpl
        implements RecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioService usuarioService;

    public RecordatorioServiceImpl(
            RecordatorioRepository recordatorioRepository,
            UsuarioService usuarioService
    ) {
        this.recordatorioRepository =
                recordatorioRepository;

        this.usuarioService =
                usuarioService;
    }

    @Override
    public Recordatorio guardarRecordatorio(
            Recordatorio recordatorio
    ) {
        return recordatorioRepository.save(
                recordatorio
        );
    }

    @Override
    public List<Recordatorio> listarRecordatoriosPorUsuario(
            Usuario usuario
    ) {
        return recordatorioRepository.findByUsuario(
                usuario
        );
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
    public Optional<Recordatorio> buscarPorId(
            Long id
    ) {
        return recordatorioRepository.findById(id);
    }

    @Override
    public void eliminarRecordatorio(
            Long id
    ) {
        recordatorioRepository.deleteById(id);
    }

    @Override
    public List<Recordatorio> listarRecordatoriosPendientesHasta(
            LocalDateTime fechaHora
    ) {
        return recordatorioRepository
                .findByEnviadoFalseAndFechaHoraBefore(
                        fechaHora
                );
    }

    @Override
    public Recordatorio crearRecordatorio(
            RecordatorioRequest request,
            String email
    ) {

        Usuario usuario =
                usuarioService
                        .buscarPorEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Usuario no encontrado"
                                )
                        );

        Recordatorio recordatorio =
                new Recordatorio(
                        request.getTitulo(),
                        request.getDescripcion(),
                        request.getFechaHora(),
                        usuario
                );

        return recordatorioRepository.save(
                recordatorio
        );
    }

    @Override
    public List<Recordatorio> listarRecordatoriosDelUsuario(
            String email
    ) {

        Usuario usuario =
                usuarioService
                        .buscarPorEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Usuario no encontrado"
                                )
                        );

        return recordatorioRepository
                .findByUsuario(usuario);
    }

    @Override
    public List<Recordatorio> listarPendientesDelUsuario(
            String email
    ) {

        Usuario usuario =
                usuarioService
                        .buscarPorEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Usuario no encontrado"
                                )
                        );

        return recordatorioRepository
                .findByUsuarioAndEnviado(
                        usuario,
                        false
                );
    }

    @Override
    public void eliminarRecordatorioDelUsuario(
            Long id,
            String email
    ) {

        Recordatorio recordatorio =
                recordatorioRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Recordatorio no encontrado"
                                )
                        );

        if (!recordatorio
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a este recordatorio"
            );
        }

        recordatorioRepository.delete(
                recordatorio
        );
    }
}
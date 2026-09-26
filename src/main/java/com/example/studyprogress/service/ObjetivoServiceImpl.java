package com.example.studyprogress.service;

import com.example.studyprogress.dto.ObjetivoRequest;
import com.example.studyprogress.event.ObjetivoCompletadoEvent;
import com.example.studyprogress.exception.ForbiddenOperationException;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.model.Objetivo;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.ObjetivoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivoServiceImpl
        implements ObjetivoService {

    private final ObjetivoRepository objetivoRepository;
    private final UsuarioService usuarioService;
    private final GamificacionService gamificacionService;
    private final ApplicationEventPublisher eventPublisher;

    public ObjetivoServiceImpl(
            ObjetivoRepository objetivoRepository,
            UsuarioService usuarioService,
            GamificacionService gamificacionService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.objetivoRepository =
                objetivoRepository;

        this.usuarioService =
                usuarioService;

        this.gamificacionService =
                gamificacionService;

        this.eventPublisher =
                eventPublisher;
    }

    @Override
    public Objetivo guardarObjetivo(
            Objetivo objetivo
    ) {
        return objetivoRepository.save(
                objetivo
        );
    }

    @Override
    public List<Objetivo> listarObjetivosPorUsuario(
            Usuario usuario
    ) {
        return objetivoRepository.findByUsuario(
                usuario
        );
    }

    @Override
    public List<Objetivo> listarObjetivosPorEstado(
            Usuario usuario,
            boolean completado
    ) {
        return objetivoRepository
                .findByUsuarioAndCompletado(
                        usuario,
                        completado
                );
    }

    @Override
    public Optional<Objetivo> buscarPorId(
            Long id
    ) {
        return objetivoRepository.findById(id);
    }

    @Override
    public void eliminarObjetivo(
            Long id
    ) {
        objetivoRepository.deleteById(id);
    }

    @Override
    public Objetivo crearObjetivo(
            ObjetivoRequest request,
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

        Objetivo objetivo =
                new Objetivo(
                        request.getTitulo(),
                        request.getDescripcion(),
                        request.getFechaLimite(),
                        usuario
                );

        return objetivoRepository.save(
                objetivo
        );
    }

    @Override
    public List<Objetivo> listarObjetivosDelUsuario(
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

        return objetivoRepository.findByUsuario(
                usuario
        );
    }

    @Override
    public Objetivo completarObjetivo(
            Long id,
            String email
    ) {

        Objetivo objetivo =
                objetivoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Objetivo no encontrado"
                                )
                        );

        if (!objetivo
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a este objetivo"
            );
        }

        if (!objetivo.isCompletado()) {

            objetivo.setCompletado(
                    true
            );

            objetivo =
                    objetivoRepository.save(
                            objetivo
                    );

            gamificacionService.sumarPuntos(
                    objetivo.getUsuario(),
                    30
            );

            eventPublisher.publishEvent(
                    new ObjetivoCompletadoEvent(
                            objetivo.getId(),
                            objetivo.getTitulo(),
                            objetivo
                                    .getUsuario()
                                    .getEmail()
                    )
            );
        }

        return objetivo;
    }

    @Override
    public void eliminarObjetivoDelUsuario(
            Long id,
            String email
    ) {

        Objetivo objetivo =
                objetivoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Objetivo no encontrado"
                                )
                        );

        if (!objetivo
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a este objetivo"
            );
        }

        objetivoRepository.delete(
                objetivo
        );
    }
}
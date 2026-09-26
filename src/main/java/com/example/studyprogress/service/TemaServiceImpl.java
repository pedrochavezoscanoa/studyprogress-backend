package com.example.studyprogress.service;

import com.example.studyprogress.dto.TemaRequest;
import com.example.studyprogress.exception.ForbiddenOperationException;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Tema;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.TemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaServiceImpl implements TemaService {

    private final TemaRepository temaRepository;
    private final CursoService cursoService;
    private final GamificacionService gamificacionService;

    public TemaServiceImpl(
            TemaRepository temaRepository,
            CursoService cursoService,
            GamificacionService gamificacionService
    ) {
        this.temaRepository = temaRepository;
        this.cursoService = cursoService;
        this.gamificacionService = gamificacionService;
    }

    @Override
    public Tema guardarTema(Tema tema) {
        return temaRepository.save(tema);
    }

    @Override
    public List<Tema> listarTemasPorCurso(Curso curso) {
        return temaRepository.findByCurso(curso);
    }

    @Override
    public Optional<Tema> buscarPorId(Long id) {
        return temaRepository.findById(id);
    }

    @Override
    public void eliminarTema(Long id) {
        temaRepository.deleteById(id);
    }

    @Override
    public Tema crearTema(
            Long cursoId,
            TemaRequest request,
            String email
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        email
                );

        Tema tema = new Tema(
                request.getNombre(),
                request.getDescripcion(),
                curso
        );

        return temaRepository.save(tema);
    }

    @Override
    public List<Tema> listarTemasDelCurso(
            Long cursoId,
            String email
    ) {

        Curso curso =
                cursoService.buscarCursoDelUsuario(
                        cursoId,
                        email
                );

        return temaRepository.findByCurso(curso);
    }

    @Override
    public Tema completarTema(
            Long id,
            String email
    ) {

        Tema tema = temaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tema no encontrado"
                        )
                );

        if (!tema.getCurso()
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a este tema"
            );
        }

        if (!tema.isCompletado()) {

            tema.setCompletado(true);

            temaRepository.save(tema);

            Usuario usuario =
                    tema.getCurso().getUsuario();

            gamificacionService.sumarPuntos(
                    usuario,
                    10
            );
        }

        return tema;
    }

    @Override
    public void eliminarTemaDelUsuario(
            Long id,
            String email
    ) {

        Tema tema = temaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tema no encontrado"
                        )
                );

        if (!tema.getCurso()
                .getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes permiso para eliminar este tema"
            );
        }

        temaRepository.delete(tema);
    }
}
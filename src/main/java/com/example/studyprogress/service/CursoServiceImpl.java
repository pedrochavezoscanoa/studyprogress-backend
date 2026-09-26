package com.example.studyprogress.service;

import com.example.studyprogress.exception.ForbiddenOperationException;
import com.example.studyprogress.exception.ResourceNotFoundException;
import com.example.studyprogress.model.Curso;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(
            CursoRepository cursoRepository
    ) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> listarCursosPorUsuario(
            Usuario usuario
    ) {
        return cursoRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso buscarCursoDelUsuario(
            Long id,
            String email
    ) {

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Curso no encontrado"
                        )
                );

        if (!curso.getUsuario()
                .getEmail()
                .equals(email)) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a este curso"
            );
        }

        return curso;
    }

    @Override
    public void eliminarCursoDelUsuario(
            Long id,
            String email
    ) {

        Curso curso =
                buscarCursoDelUsuario(id, email);

        cursoRepository.delete(curso);
    }
}
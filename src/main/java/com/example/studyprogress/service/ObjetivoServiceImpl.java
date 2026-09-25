package com.example.studyprogress.service;

import com.example.studyprogress.model.Objetivo;
import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.ObjetivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivoServiceImpl implements ObjetivoService {

    private final ObjetivoRepository objetivoRepository;

    public ObjetivoServiceImpl(
            ObjetivoRepository objetivoRepository
    ) {
        this.objetivoRepository = objetivoRepository;
    }

    @Override
    public Objetivo guardarObjetivo(Objetivo objetivo) {
        return objetivoRepository.save(objetivo);
    }

    @Override
    public List<Objetivo> listarObjetivosPorUsuario(Usuario usuario) {
        return objetivoRepository.findByUsuario(usuario);
    }

    @Override
    public List<Objetivo> listarObjetivosPorEstado(
            Usuario usuario,
            boolean completado
    ) {
        return objetivoRepository.findByUsuarioAndCompletado(
                usuario,
                completado
        );
    }

    @Override
    public Optional<Objetivo> buscarPorId(Long id) {
        return objetivoRepository.findById(id);
    }

    @Override
    public void eliminarObjetivo(Long id) {
        objetivoRepository.deleteById(id);
    }
}
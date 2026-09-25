package com.example.studyprogress.service;

import com.example.studyprogress.model.Objetivo;
import com.example.studyprogress.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface ObjetivoService {

    Objetivo guardarObjetivo(Objetivo objetivo);

    List<Objetivo> listarObjetivosPorUsuario(Usuario usuario);

    List<Objetivo> listarObjetivosPorEstado(
            Usuario usuario,
            boolean completado
    );

    Optional<Objetivo> buscarPorId(Long id);

    void eliminarObjetivo(Long id);
}
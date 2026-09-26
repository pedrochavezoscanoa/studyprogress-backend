package com.example.studyprogress.service;

import com.example.studyprogress.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario guardarUsuario(
            Usuario usuario
    );

    List<Usuario> listarUsuarios();

    Optional<Usuario> buscarPorId(
            Long id
    );

    Optional<Usuario> buscarPorEmail(
            String email
    );

    boolean existePorEmail(
            String email
    );

    void eliminarUsuario(
            Long id
    );

    Usuario obtenerUsuarioPorId(
            Long id
    );

    void eliminarUsuarioPorId(
            Long id
    );
}
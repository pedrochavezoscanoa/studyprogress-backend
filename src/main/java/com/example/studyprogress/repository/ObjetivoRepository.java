package com.example.studyprogress.repository;

import com.example.studyprogress.model.Objetivo;
import com.example.studyprogress.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjetivoRepository extends JpaRepository<Objetivo, Long> {

    List<Objetivo> findByUsuario(Usuario usuario);

    List<Objetivo> findByUsuarioAndCompletado(
            Usuario usuario,
            boolean completado
    );
}
package com.example.studyprogress.service;

import com.example.studyprogress.model.Usuario;
import com.example.studyprogress.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class GamificacionServiceImpl implements GamificacionService {

    private final UsuarioRepository usuarioRepository;

    public GamificacionServiceImpl(
            UsuarioRepository usuarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario sumarPuntos(
            Usuario usuario,
            int puntos
    ) {

        usuario.setPuntos(
                usuario.getPuntos() + puntos
        );

        return usuarioRepository.save(usuario);
    }

    @Override
    public String obtenerNivel(Usuario usuario) {

        int puntos = usuario.getPuntos();

        if (puntos >= 500) {
            return "AVANZADO";
        }

        if (puntos >= 200) {
            return "INTERMEDIO";
        }

        return "PRINCIPIANTE";
    }
}
package com.example.studyprogress.mapper;

import com.example.studyprogress.dto.UsuarioResponseDTO;
import com.example.studyprogress.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toDTO(
            Usuario usuario
    ) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPuntos(),
                usuario.getRol()
        );
    }
}
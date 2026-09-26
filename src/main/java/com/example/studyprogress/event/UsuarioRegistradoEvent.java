package com.example.studyprogress.event;

public record UsuarioRegistradoEvent(
        Long usuarioId,
        String nombre,
        String email
) {
}
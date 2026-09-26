package com.example.studyprogress.event;

public record ObjetivoCompletadoEvent(
        Long objetivoId,
        String titulo,
        String email
) {
}
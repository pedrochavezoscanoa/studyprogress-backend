package com.example.studyprogress.service;

public interface EmailService {

    String enviarCorreo(
            String destinatario,
            String asunto,
            String contenidoHtml
    );
}
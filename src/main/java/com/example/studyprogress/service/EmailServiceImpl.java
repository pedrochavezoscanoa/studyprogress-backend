package com.example.studyprogress.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final Resend resend;
    private final String fromEmail;

    public EmailServiceImpl(
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from-email}") String fromEmail
    ) {
        this.resend = new Resend(apiKey);
        this.fromEmail = fromEmail;
    }

    @Override
    public String enviarCorreo(
            String destinatario,
            String asunto,
            String contenidoHtml
    ) {

        CreateEmailOptions email = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(destinatario)
                .subject(asunto)
                .html(contenidoHtml)
                .build();

        try {

            CreateEmailResponse response =
                    resend.emails().send(email);

            return response.getId();

        } catch (ResendException e) {

            throw new RuntimeException(
                    "Error al enviar el correo",
                    e
            );
        }
    }
}
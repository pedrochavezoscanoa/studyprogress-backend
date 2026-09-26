package com.example.studyprogress.listener;

import com.example.studyprogress.event.UsuarioRegistradoEvent;
import com.example.studyprogress.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEventListener {

    private final EmailService emailService;

    public UsuarioEventListener(
            EmailService emailService
    ) {
        this.emailService = emailService;
    }

    @Async("taskExecutor")
    @EventListener
    public void manejarUsuarioRegistrado(
            UsuarioRegistradoEvent event
    ) {

        try {

            String asunto =
                    "Bienvenido a StudyProgress";

            String contenidoHtml =
                    "<h2>Bienvenido "
                            + event.nombre()
                            + "</h2>"
                            + "<p>Tu cuenta en StudyProgress "
                            + "fue creada correctamente.</p>";

            emailService.enviarCorreo(
                    event.email(),
                    asunto,
                    contenidoHtml
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "No se pudo enviar el correo de bienvenida: "
                            + e.getMessage()
            );
        }
    }
}
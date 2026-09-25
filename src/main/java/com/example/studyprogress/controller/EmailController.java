package com.example.studyprogress.controller;

import com.example.studyprogress.dto.EmailPruebaRequest;
import com.example.studyprogress.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/prueba")
    public ResponseEntity<?> enviarCorreoPrueba(
            @RequestBody EmailPruebaRequest request
    ) {

        try {

            String idCorreo = emailService.enviarCorreo(
                    request.getDestinatario(),
                    "Prueba de StudyProgress",
                    """
                    <h2>StudyProgress</h2>
                    <p>El envío de correos está funcionando correctamente.</p>
                    <p>Este es un correo de prueba.</p>
                    """
            );

            return ResponseEntity.ok(
                    "Correo enviado correctamente. ID: " + idCorreo
            );

        } catch (RuntimeException e) {

            e.printStackTrace();

            String detalle = e.getMessage();

            if (e.getCause() != null) {
                detalle = e.getCause().getMessage();
            }

            return ResponseEntity.internalServerError()
                    .body("Error al enviar correo: " + detalle);
        }
    }
}
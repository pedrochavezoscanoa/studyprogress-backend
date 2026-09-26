package com.example.studyprogress.listener;

import com.example.studyprogress.event.RecordatorioVencidoEvent;
import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.service.EmailService;
import com.example.studyprogress.service.RecordatorioService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecordatorioEventListener {

    private final RecordatorioService recordatorioService;
    private final EmailService emailService;

    public RecordatorioEventListener(
            RecordatorioService recordatorioService,
            EmailService emailService
    ) {
        this.recordatorioService =
                recordatorioService;

        this.emailService =
                emailService;
    }

    @Async("taskExecutor")
    @EventListener
    public void manejarRecordatorioVencido(
            RecordatorioVencidoEvent event
    ) {

        Optional<Recordatorio> optional =
                recordatorioService.buscarPorId(
                        event.recordatorioId()
                );

        if (optional.isEmpty()) {
            return;
        }

        Recordatorio recordatorio =
                optional.get();

        if (recordatorio.isEnviado()) {
            return;
        }

        try {

            String asunto =
                    "Recordatorio: "
                            + recordatorio.getTitulo();

            String contenidoHtml =
                    "<h2>"
                            + recordatorio.getTitulo()
                            + "</h2>"
                            + "<p>"
                            + recordatorio.getDescripcion()
                            + "</p>"
                            + "<p><strong>Fecha:</strong> "
                            + recordatorio.getFechaHora()
                            + "</p>";

            emailService.enviarCorreo(
                    recordatorio
                            .getUsuario()
                            .getEmail(),
                    asunto,
                    contenidoHtml
            );

            recordatorio.setEnviado(true);

            recordatorioService.guardarRecordatorio(
                    recordatorio
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Error enviando recordatorio "
                            + recordatorio.getId()
                            + ": "
                            + e.getMessage()
            );
        }
    }
}
package com.example.studyprogress.service;

import com.example.studyprogress.model.Recordatorio;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RecordatorioScheduler {

    private final RecordatorioService recordatorioService;
    private final EmailService emailService;

    public RecordatorioScheduler(
            RecordatorioService recordatorioService,
            EmailService emailService
    ) {
        this.recordatorioService = recordatorioService;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void procesarRecordatorios() {

        List<Recordatorio> recordatorios =
                recordatorioService.listarPendientesParaEnviar(
                        LocalDateTime.now()
                );

        for (Recordatorio recordatorio : recordatorios) {

            try {

                String destinatario =
                        recordatorio.getUsuario().getEmail();

                String asunto =
                        "Recordatorio StudyProgress: "
                                + recordatorio.getTitulo();

                String contenido = """
                        <h2>StudyProgress</h2>
                        <h3>%s</h3>
                        <p>%s</p>
                        <p>Este es un recordatorio automático de StudyProgress.</p>
                        """.formatted(
                        recordatorio.getTitulo(),
                        recordatorio.getDescripcion()
                );

                emailService.enviarCorreo(
                        destinatario,
                        asunto,
                        contenido
                );

                recordatorio.setEnviado(true);

                recordatorioService.guardarRecordatorio(
                        recordatorio
                );

                System.out.println(
                        "Recordatorio enviado: "
                                + recordatorio.getId()
                );

            } catch (RuntimeException e) {

                System.out.println(
                        "No se pudo enviar el recordatorio "
                                + recordatorio.getId()
                );

                e.printStackTrace();
            }
        }
    }
}
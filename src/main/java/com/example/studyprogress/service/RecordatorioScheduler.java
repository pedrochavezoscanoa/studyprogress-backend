package com.example.studyprogress.service;

import com.example.studyprogress.event.RecordatorioVencidoEvent;
import com.example.studyprogress.model.Recordatorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RecordatorioScheduler {

    private final RecordatorioService recordatorioService;
    private final ApplicationEventPublisher eventPublisher;

    public RecordatorioScheduler(
            RecordatorioService recordatorioService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.recordatorioService =
                recordatorioService;

        this.eventPublisher =
                eventPublisher;
    }

    @Scheduled(fixedRate = 60000)
    public void procesarRecordatorios() {

        List<Recordatorio> recordatorios =
                recordatorioService
                        .listarRecordatoriosPendientesHasta(
                                LocalDateTime.now()
                        );

        for (Recordatorio recordatorio
                : recordatorios) {

            eventPublisher.publishEvent(
                    new RecordatorioVencidoEvent(
                            recordatorio.getId()
                    )
            );
        }
    }
}
package com.example.studyprogress.listener;

import com.example.studyprogress.event.ObjetivoCompletadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ObjetivoEventListener {

    @EventListener
    public void manejarObjetivoCompletado(
            ObjetivoCompletadoEvent event
    ) {

        System.out.println(
                "Evento: objetivo completado -> "
                        + event.objetivoId()
                        + " - "
                        + event.titulo()
                        + " - "
                        + event.email()
        );
    }
}
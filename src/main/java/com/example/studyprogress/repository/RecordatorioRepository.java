package com.example.studyprogress.repository;

import com.example.studyprogress.model.Recordatorio;
import com.example.studyprogress.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {

    List<Recordatorio> findByUsuario(Usuario usuario);

    List<Recordatorio> findByUsuarioAndEnviado(
            Usuario usuario,
            boolean enviado
    );

    List<Recordatorio> findByEnviadoFalseAndFechaHoraBefore(
            LocalDateTime fechaHora
    );
}
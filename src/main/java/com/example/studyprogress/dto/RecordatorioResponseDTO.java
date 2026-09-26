package com.example.studyprogress.dto;

import java.time.LocalDateTime;

public class RecordatorioResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private boolean enviado;

    public RecordatorioResponseDTO() {
    }

    public RecordatorioResponseDTO(
            Long id,
            String titulo,
            String descripcion,
            LocalDateTime fechaHora,
            boolean enviado
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.enviado = enviado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }
}
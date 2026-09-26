package com.example.studyprogress.dto;

import java.time.LocalDate;

public class ObjetivoResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaLimite;
    private boolean completado;

    public ObjetivoResponseDTO() {
    }

    public ObjetivoResponseDTO(
            Long id,
            String titulo,
            String descripcion,
            LocalDate fechaLimite,
            boolean completado
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.completado = completado;
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

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
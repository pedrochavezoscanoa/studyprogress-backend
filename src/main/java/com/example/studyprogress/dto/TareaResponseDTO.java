package com.example.studyprogress.dto;

import java.time.LocalDate;

public class TareaResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaLimite;
    private String prioridad;
    private boolean completada;
    private Long cursoId;

    public TareaResponseDTO() {
    }

    public TareaResponseDTO(
            Long id,
            String titulo,
            String descripcion,
            LocalDate fechaLimite,
            String prioridad,
            boolean completada,
            Long cursoId
    ) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridad;
        this.completada = completada;
        this.cursoId = cursoId;
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
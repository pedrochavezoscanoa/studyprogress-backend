package com.example.studyprogress.service;

import com.example.studyprogress.model.Usuario;

public interface GamificacionService {

    Usuario sumarPuntos(Usuario usuario, int puntos);

    String obtenerNivel(Usuario usuario);
}
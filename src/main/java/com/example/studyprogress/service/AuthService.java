package com.example.studyprogress.service;

import com.example.studyprogress.dto.LoginRequest;
import com.example.studyprogress.dto.RegistroRequest;
import com.example.studyprogress.model.Usuario;

public interface AuthService {

    Usuario registrar(RegistroRequest request);

    String login(LoginRequest request);
}
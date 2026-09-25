package com.example.studyprogress.service;

import com.example.studyprogress.dto.LoginRequest;
import com.example.studyprogress.dto.RegistroRequest;
import com.example.studyprogress.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Usuario registrar(RegistroRequest request) {

        if (usuarioService.existePorEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario(
                request.getNombre(),
                request.getEmail(),
                request.getPassword()
        );

        return usuarioService.guardarUsuario(usuario);
    }

    @Override
    public String login(LoginRequest request) {

        Optional<Usuario> usuarioOptional =
                usuarioService.buscarPorEmail(request.getEmail());

        if (usuarioOptional.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioOptional.get();

        boolean passwordCorrecta = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
        );

        if (!passwordCorrecta) {
            return null;
        }

        return jwtService.generarToken(usuario.getEmail());
    }
}
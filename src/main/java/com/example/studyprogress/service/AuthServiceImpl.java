package com.example.studyprogress.service;

import com.example.studyprogress.dto.LoginRequest;
import com.example.studyprogress.dto.RegistroRequest;
import com.example.studyprogress.event.UsuarioRegistradoEvent;
import com.example.studyprogress.exception.DuplicateResourceException;
import com.example.studyprogress.exception.InvalidCredentialsException;
import com.example.studyprogress.model.Usuario;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl
        implements AuthService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthServiceImpl(
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.usuarioService =
                usuarioService;

        this.passwordEncoder =
                passwordEncoder;

        this.jwtService =
                jwtService;

        this.eventPublisher =
                eventPublisher;
    }

    @Override
    public Usuario registrar(
            RegistroRequest request
    ) {

        if (usuarioService
                .existePorEmail(
                        request.getEmail()
                )) {

            throw new DuplicateResourceException(
                    "El email ya está registrado"
            );
        }

        Usuario usuario =
                new Usuario(
                        request.getNombre(),
                        request.getEmail(),
                        request.getPassword()
                );

        Usuario nuevoUsuario =
                usuarioService.guardarUsuario(
                        usuario
                );

        eventPublisher.publishEvent(
                new UsuarioRegistradoEvent(
                        nuevoUsuario.getId(),
                        nuevoUsuario.getNombre(),
                        nuevoUsuario.getEmail()
                )
        );

        return nuevoUsuario;
    }

    @Override
    public String login(
            LoginRequest request
    ) {

        Optional<Usuario> usuarioOptional =
                usuarioService.buscarPorEmail(
                        request.getEmail()
                );

        if (usuarioOptional.isEmpty()) {

            throw new InvalidCredentialsException(
                    "Email o contraseña incorrectos"
            );
        }

        Usuario usuario =
                usuarioOptional.get();

        boolean passwordCorrecta =
                passwordEncoder.matches(
                        request.getPassword(),
                        usuario.getPassword()
                );

        if (!passwordCorrecta) {

            throw new InvalidCredentialsException(
                    "Email o contraseña incorrectos"
            );
        }

        return jwtService.generarToken(
                usuario
        );
    }
}
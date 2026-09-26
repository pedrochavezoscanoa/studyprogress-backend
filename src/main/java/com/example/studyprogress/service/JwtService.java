package com.example.studyprogress.service;

import com.example.studyprogress.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generarToken(
            Usuario usuario
    ) {

        Date ahora = new Date();

        Date fechaExpiracion =
                new Date(
                        ahora.getTime() + expiration
                );

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("userId", usuario.getId())
                .claim("email", usuario.getEmail())
                .claim("role", usuario.getRol())
                .issuedAt(ahora)
                .expiration(fechaExpiracion)
                .signWith(obtenerClave())
                .compact();
    }

    public Claims obtenerClaims(
            String token
    ) {

        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extraerEmail(
            String token
    ) {

        return obtenerClaims(token)
                .get(
                        "email",
                        String.class
                );
    }

    public String extraerRol(
            String token
    ) {

        return obtenerClaims(token)
                .get(
                        "role",
                        String.class
                );
    }

    public Long extraerUsuarioId(
            String token
    ) {

        Number usuarioId =
                obtenerClaims(token)
                        .get(
                                "userId",
                                Number.class
                        );

        if (usuarioId == null) {
            return null;
        }

        return usuarioId.longValue();
    }

    public boolean validarToken(
            String token
    ) {

        try {

            Claims claims =
                    obtenerClaims(token);

            Date expiracion =
                    claims.getExpiration();

            return expiracion != null
                    && expiracion.after(
                    new Date()
            );

        } catch (
                JwtException |
                IllegalArgumentException e
        ) {

            return false;
        }
    }
}
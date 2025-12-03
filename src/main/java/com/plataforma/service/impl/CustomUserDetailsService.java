package com.plataforma.service.impl;

import com.plataforma.domain.Usuario;
import com.plataforma.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + email);
        }

        String rolSpring = "ROLE_" + usuario.getRol().getNombre(); 
        // ADMIN → ROLE_ADMIN
        // PROFESOR → ROLE_PROFESOR
        // ESTUDIANTE → ROLE_ESTUDIANTE

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())    // ya encriptada
                .roles(usuario.getRol().getNombre()) // sin el "ROLE_" para builder
                .disabled(!usuario.getActivo())
                .build();
    }
}


package com.plataforma.service.impl;

import com.plataforma.domain.Usuario;
import com.plataforma.repository.UsuarioRepository;
import com.plataforma.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Consultas avanzadas
    @Override
    public List<Usuario> buscarPorRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }

    @Override
    public List<Usuario> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return usuarioRepository.findByFechaCreacionBetween(inicio, fin);
    }

    @Override
    public List<Usuario> buscarPorTexto(String texto) {
        return usuarioRepository.findByEmailContainingIgnoreCaseOrNombreContainingIgnoreCase(texto, texto);
    }

    @Override
    public Long contarActivos() {
        return usuarioRepository.countByActivoTrue();
    }

    @Override
    public Long contarInactivos() {
        return usuarioRepository.countByActivoFalse();
    }

    @Override
    public List<Usuario> listarOrdenadosPorFecha() {
        return usuarioRepository.findAllByOrderByFechaCreacionDesc();
    }
}

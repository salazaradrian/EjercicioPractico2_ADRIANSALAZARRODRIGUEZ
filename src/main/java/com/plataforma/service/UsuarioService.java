package com.plataforma.service;

import com.plataforma.domain.Usuario;

import java.util.List;
import java.time.LocalDateTime;

public interface UsuarioService {

    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Usuario buscarPorId(Long id);

    void eliminar(Long id);

    // Consultas avanzadas
    List<Usuario> buscarPorRol(String nombreRol);

    List<Usuario> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);

    List<Usuario> buscarPorTexto(String texto);

    Long contarActivos();

    Long contarInactivos();

    List<Usuario> listarOrdenadosPorFecha();
}

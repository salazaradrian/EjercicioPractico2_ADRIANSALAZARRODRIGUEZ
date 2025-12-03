package com.plataforma.repository;

import com.plataforma.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Para login
    Usuario findByEmail(String email);

   
    List<Usuario> findByRolNombre(String nombreRol);

    List<Usuario> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Usuario> findByEmailContainingIgnoreCaseOrNombreContainingIgnoreCase(
            String email, String nombre
    );

    Long countByActivoTrue();
    Long countByActivoFalse();

    List<Usuario> findAllByOrderByFechaCreacionDesc();
}

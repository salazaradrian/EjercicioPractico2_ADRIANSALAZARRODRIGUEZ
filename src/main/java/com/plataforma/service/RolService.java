package com.plataforma.service;

import com.plataforma.domain.Rol;
import java.util.List;

public interface RolService {

    List<Rol> listarTodos();

    Rol guardar(Rol rol);

    Rol buscarPorId(Long id);

    void eliminar(Long id);
}

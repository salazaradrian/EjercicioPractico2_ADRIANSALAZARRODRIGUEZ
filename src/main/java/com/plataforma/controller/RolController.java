package com.plataforma.controller;

import com.plataforma.domain.Rol;
import com.plataforma.service.RolService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.listarTodos());
        return "roles/listado";
    }

    @GetMapping("/nuevo")
    public String nuevoRol(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/form";
    }

    @PostMapping("/guardar")
    public String guardarRol(Rol rol) {
        rolService.guardar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable Long id, Model model) {
        model.addAttribute("rol", rolService.buscarPorId(id));
        return "roles/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Long id) {
        rolService.eliminar(id);
        return "redirect:/roles";
    }
}


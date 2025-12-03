package com.plataforma.controller;

import com.plataforma.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/consultas")
public class ConsultaController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String consultas() {
        return "consultas/index";
    }

    @GetMapping("/activos")
    public String activos(Model model) {
        model.addAttribute("total", usuarioService.contarActivos());
        return "consultas/activos";
    }

    @GetMapping("/inactivos")
    public String inactivos(Model model) {
        model.addAttribute("total", usuarioService.contarInactivos());
        return "consultas/inactivos";
    }

    @GetMapping("/ordenados")
    public String ordenados(Model model) {
        model.addAttribute("usuarios", usuarioService.listarOrdenadosPorFecha());
        return "consultas/ordenados";
    }
}

package com.plataforma.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        model.addAttribute("email", auth.getName());
        return "perfil";
    }
}

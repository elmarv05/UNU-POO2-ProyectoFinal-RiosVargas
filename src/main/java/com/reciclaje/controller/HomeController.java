package com.reciclaje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reciclaje.model.Trabajador;
import com.reciclaje.service.TrabajadorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private TrabajadorService trabajadorService;

    
    @GetMapping("/")
    public String index() {
        return "redirect:/login"; 
    }

    
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,@RequestParam String password, HttpSession session, Model model) {
        
        Trabajador t = trabajadorService.validarCredenciales(username, password);

        if (t != null) {
        	session.setAttribute("usuarioLogueado", t);
            return "redirect:/web/home"; 
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }
    
    @GetMapping("/web/home")
    public String home(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        return "home"; 
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
package com.reciclaje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Trabajador;
import com.reciclaje.repository.RolRepository; // O RolService si lo tienes creado
import com.reciclaje.service.TrabajadorService;

@Controller
@RequestMapping("/web/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private RolRepository rolRepository; // Necesario para el desplegable de Roles

    // 1. LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("trabajadores", trabajadorService.listarTodos());
        return "trabajadores/listaTrabajadores";
    }

    // 2. NUEVO
    @GetMapping("/registrar")
    public String registrar(Model model) {
        Trabajador t = new Trabajador();
        t.setActivo(true); // Por defecto activo
        model.addAttribute("trabajador", t);
        
        // ¡IMPORTANTE! Cargar roles
        model.addAttribute("roles", rolRepository.findAll());
        return "trabajadores/formularioTrabajadores";
    }

    // 3. EDITAR (EL QUE FALTABA)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Trabajador t = trabajadorService.buscarPorId(id);
        if (t == null) {
            return "redirect:/web/trabajadores";
        }
        model.addAttribute("trabajador", t);
        
        // ¡IMPORTANTE! Cargar roles también al editar
        model.addAttribute("roles", rolRepository.findAll());
        return "trabajadores/formularioTrabajadores";
    }

    // 4. GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Trabajador trabajador) {
        trabajadorService.guardar(trabajador);
        return "redirect:/web/trabajadores";
    }

    // 5. ELIMINAR (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        trabajadorService.eliminar(id); // Asegúrate que tu Service haga setActivo(false)
        return "redirect:/web/trabajadores";
    }
}
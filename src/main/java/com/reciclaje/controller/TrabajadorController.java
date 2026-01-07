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
    public String listar(@RequestParam(required = false) Boolean mostrarTodos,
            @RequestParam(required = false) Integer rolId,
            Model model) {
        boolean mostrar = (mostrarTodos != null) && mostrarTodos;

        if (rolId != null) {
            if (mostrar) {
                model.addAttribute("trabajadores", trabajadorService.listarPorRol(rolId));
            } else {
                model.addAttribute("trabajadores", trabajadorService.listarActivosPorRol(rolId));
            }
            model.addAttribute("rolSeleccionado", rolId);
        } else {
            if (mostrar) {
                model.addAttribute("trabajadores", trabajadorService.listarTodos());
            } else {
                model.addAttribute("trabajadores", trabajadorService.listarActivos());
            }
        }

        model.addAttribute("mostrarTodos", mostrar);
        model.addAttribute("roles", rolRepository.findAll()); // Para el filtro desplegable
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

    // 3. EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
            @RequestParam(required = false) Integer rolId,
            @RequestParam(required = false) Boolean mostrarTodos,
            Model model) {
        Trabajador t = trabajadorService.buscarPorId(id);
        if (t == null) {
            return "redirect:/web/trabajadores";
        }
        model.addAttribute("trabajador", t);

        model.addAttribute("roles", rolRepository.findAll());

        // Persistir Filtros
        model.addAttribute("filtroRolId", rolId);
        model.addAttribute("filtroMostrarTodos", mostrarTodos);

        return "trabajadores/formularioTrabajadores";
    }

    // 4. GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Trabajador trabajador,
            @RequestParam(required = false) Integer filtroRolId,
            @RequestParam(required = false) Boolean filtroMostrarTodos,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        trabajadorService.guardar(trabajador);

        // Restaurar filtros
        if (filtroRolId != null)
            redirectAttributes.addAttribute("rolId", filtroRolId);
        if (filtroMostrarTodos != null && filtroMostrarTodos)
            redirectAttributes.addAttribute("mostrarTodos", true);

        return "redirect:/web/trabajadores";
    }

    // 5. ELIMINAR (Soft Delete)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id,
            @RequestParam(required = false) Integer rolId,
            @RequestParam(required = false) Boolean mostrarTodos,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        trabajadorService.eliminar(id);

        // Restaurar filtros
        if (rolId != null)
            redirectAttributes.addAttribute("rolId", rolId);
        if (mostrarTodos != null && mostrarTodos)
            redirectAttributes.addAttribute("mostrarTodos", true);

        return "redirect:/web/trabajadores";
    }
}
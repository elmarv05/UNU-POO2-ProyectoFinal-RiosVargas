package com.reciclaje.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reciclaje.model.Trabajador;
import com.reciclaje.service.TrabajadorService;
import com.reciclaje.repository.RolRepository; 

@Controller
@RequestMapping("/web/trabajadores")
public class TrabajadorController {
    
    @Autowired
    private TrabajadorService trabajadorService;
    
    @Autowired
    private RolRepository rolRepository; 
    
    
    @GetMapping
    public String listarTrabajadores(Model model) {
        model.addAttribute("trabajadores", trabajadorService.listarTodos());
        return "trabajadores/listaTrabajadores";
    }
    
    
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("trabajador", new Trabajador());
        
        model.addAttribute("listaRoles", rolRepository.findAll());
        return "trabajadores/formularioTrabajadores";
    }
    
    
    @PostMapping
    public String guardarTrabajador(@ModelAttribute Trabajador trabajador) {
        trabajadorService.guardar(trabajador);
        return "redirect:/web/trabajadores";
    }
    
    
    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        Optional<Trabajador> trabajadorOpt = Optional.ofNullable(trabajadorService.buscarPorId(id));
        
        if(trabajadorOpt.isPresent()) {
            model.addAttribute("trabajador", trabajadorOpt.get());
            
            model.addAttribute("listaRoles", rolRepository.findAll()); 
            return "trabajadores/formularioTrabajadores";
        }
        return "redirect:/web/trabajadores";
    }
    
   
    @GetMapping("/{id}/eliminar")
    public String eliminarTrabajador(@PathVariable Integer id) {
        trabajadorService.eliminar(id);
        return "redirect:/web/trabajadores";
    }
}
package com.reciclaje.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Proveedor;
import com.reciclaje.service.ProveedorService;

@Controller
@RequestMapping("/web/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

   
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "proveedores/listaProveedores"; 
    } 

    
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/formularioProveedores"; 
    }

 
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Proveedor proveedor) {
        proveedorService.guardar(proveedor);
        return "redirect:/web/proveedores";
    }

   
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        Optional<Proveedor> provOpt = Optional.ofNullable(proveedorService.buscarPorId(id));
        if (provOpt.isPresent()) {
            model.addAttribute("proveedor", provOpt.get());
            return "proveedores/formularioProveedores";
        }
        return "redirect:/web/proveedores";
    }

   
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        proveedorService.eliminar(id);
        return "redirect:/web/proveedores";
    }
}
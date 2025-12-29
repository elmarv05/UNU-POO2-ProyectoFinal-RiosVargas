package com.reciclaje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Cliente;
import com.reciclaje.service.ClienteService;

@Controller
@RequestMapping("/web/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // 1. LISTAR (Solo activos por defecto o todos según prefieras, aquí mostramos todos para ver el estado)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/listaClientes";
    }

    // 2. FORMULARIO DE REGISTRO
    @GetMapping("/registrar")
    public String registrar(Model model) {
        Cliente c = new Cliente();
        c.setActivo(true); // Por defecto activo
        model.addAttribute("cliente", c);
        return "clientes/formularioClientes";
    }

    // 3. EDITAR (ESTE ES EL MÉTODO QUE FALTABA)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        
        // Validación: Si no existe, volvemos a la lista
        if (cliente == null) {
            return "redirect:/web/clientes";
        }
        
        model.addAttribute("cliente", cliente);
        return "clientes/formularioClientes"; // Reutilizamos el mismo formulario
    }

    // 4. GUARDAR (Sirve para Crear y Actualizar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        // Si es edición, el ID ya viene en el objeto cliente
        clienteService.guardar(cliente); 
        return "redirect:/web/clientes";
    }

    // 5. ELIMINAR (Borrado Lógico)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id); // Recuerda que en el Service cambiamos esto por un soft-delete
        return "redirect:/web/clientes";
    }
}
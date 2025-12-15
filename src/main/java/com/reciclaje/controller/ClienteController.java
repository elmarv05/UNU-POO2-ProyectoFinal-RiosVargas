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

import com.reciclaje.model.Cliente;
import com.reciclaje.service.ClienteService;

@Controller
@RequestMapping("/web/clientes")
public class ClienteController {
    
    
    @Autowired
    private ClienteService clienteService;
    
    
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/listaClientes"; 
    }
    
    
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formularioClientes"; 
    }
    
    
    @PostMapping
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/web/clientes";
    }
    
    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        
        if(clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            return "clientes/formularioClientes";
        }
        return "redirect:/web/clientes";
    }
    
    
    @GetMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return "redirect:/web/clientes";
    }
}
package com.reciclaje.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Categoria;
import com.reciclaje.service.CategoriaService;

@Controller
@RequestMapping("/web/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/listaCategorias";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formularioCategorias";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/web/categorias";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Categoria> catOpt = Optional.ofNullable(categoriaService.buscarPorId(id));
        if (catOpt.isPresent()) {
            model.addAttribute("categoria", catOpt.get());
            return "categorias/formularioCategorias";
        }
        return "redirect:/web/categorias";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return "redirect:/web/categorias";
    }
}
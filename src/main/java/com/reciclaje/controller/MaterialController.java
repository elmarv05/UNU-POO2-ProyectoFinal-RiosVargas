package com.reciclaje.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Material;
import com.reciclaje.service.CategoriaService;
import com.reciclaje.service.MaterialService;

@Controller
@RequestMapping("/web/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CategoriaService categoriaService; 

    // LISTAR (Ahora enviamos 2 listas separadas)
    @GetMapping
    public String listar(Model model) {
        // Lista 1: Para la tabla de Materia Prima
        model.addAttribute("residuos", materialService.listarPorTipo("RESIDUO"));
        
        // Lista 2: Para la tabla de Productos Terminados
        model.addAttribute("productos", materialService.listarPorTipo("PRODUCTO"));
        
        return "materiales/listaMateriales";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("listaCategorias", categoriaService.listarTodas());
        return "materiales/formularioMateriales";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Material material) {
        materialService.guardar(material);
        return "redirect:/web/materiales";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Optional<Material> matOpt = Optional.ofNullable(materialService.buscarPorId(id));
        if (matOpt.isPresent()) {
            model.addAttribute("material", matOpt.get());
            model.addAttribute("listaCategorias", categoriaService.listarTodas());
            return "materiales/formularioMateriales";
        }
        return "redirect:/web/materiales";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        materialService.eliminar(id);
        return "redirect:/web/materiales";
    }
}
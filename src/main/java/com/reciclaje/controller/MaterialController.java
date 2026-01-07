package com.reciclaje.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Material;
import com.reciclaje.service.CategoriaService;
import com.reciclaje.service.MaterialService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CategoriaService categoriaService;

    // LISTAR (Ahora enviamos 2 listas separadas)
    @GetMapping
    public String listar(@RequestParam(required = false) Boolean mostrarTodos,
            @RequestParam(required = false) Integer categoriaId,
            Model model) {
        boolean mostrar = (mostrarTodos != null) && mostrarTodos;

        if (categoriaId != null) {
            if (mostrar) {
                model.addAttribute("residuos", materialService.listarPorTipoYCategoria("RESIDUO", categoriaId));
                model.addAttribute("productos", materialService.listarPorTipoYCategoria("PRODUCTO", categoriaId));
            } else {
                model.addAttribute("residuos", materialService.listarActivosPorTipoYCategoria("RESIDUO", categoriaId));
                model.addAttribute("productos",
                        materialService.listarActivosPorTipoYCategoria("PRODUCTO", categoriaId));
            }
            model.addAttribute("categoriaSeleccionada", categoriaId);
        } else {
            if (mostrar) {
                model.addAttribute("residuos", materialService.listarPorTipo("RESIDUO"));
                model.addAttribute("productos", materialService.listarPorTipo("PRODUCTO"));
            } else {
                model.addAttribute("residuos", materialService.listarActivosPorTipo("RESIDUO"));
                model.addAttribute("productos", materialService.listarActivosPorTipo("PRODUCTO"));
            }
        }

        model.addAttribute("mostrarTodos", mostrar);
        model.addAttribute("categorias", categoriaService.listarTodas()); // Para el filtro desplegable

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
    public String guardar(@ModelAttribute Material material,
            @RequestParam(required = false) Integer filtroCategoriaId,
            @RequestParam(required = false) Boolean filtroMostrarTodos,
            RedirectAttributes redirectAttributes) {

        materialService.guardar(material);

        // Restaurar filtros
        if (filtroCategoriaId != null)
            redirectAttributes.addAttribute("categoriaId", filtroCategoriaId);
        if (filtroMostrarTodos != null && filtroMostrarTodos)
            redirectAttributes.addAttribute("mostrarTodos", true);

        return "redirect:/web/materiales";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Boolean mostrarTodos,
            Model model) {
        Optional<Material> matOpt = Optional.ofNullable(materialService.buscarPorId(id));
        if (matOpt.isPresent()) {
            model.addAttribute("material", matOpt.get());
            model.addAttribute("listaCategorias", categoriaService.listarTodas());

            // Pasar filtros para que el form los preserve al guardar
            model.addAttribute("filtroCategoriaId", categoriaId);
            model.addAttribute("filtroMostrarTodos", mostrarTodos);

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
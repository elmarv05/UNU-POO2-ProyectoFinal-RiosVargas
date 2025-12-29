package com.reciclaje.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reciclaje.model.Transformacion;
import com.reciclaje.model.Trabajador;
import com.reciclaje.service.MaterialService;
import com.reciclaje.service.TrabajadorService;
import com.reciclaje.service.TransformacionService;

@Controller
@RequestMapping("/web/transformaciones")
public class TransformacionController {

    @Autowired
    private TransformacionService transformacionService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private TrabajadorService trabajadorService;

    // 1. LISTAR HISTORIAL
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("transformaciones", transformacionService.listarTodas());
        return "transformaciones/listaTransformaciones";
    }

    // 2. FORMULARIO NUEVA TRANSFORMACIÓN
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("transformacion", new Transformacion());
        cargarListas(model);
        return "transformaciones/formularioTransformacion";
    }

    // 3. GUARDAR (PROCESAR)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Transformacion transformacion, Model model) {
        try {
            // Asignar trabajador (Temporal: Hardcodeamos al Admin ID 1)
            Trabajador t = trabajadorService.buscarPorId(1);
            transformacion.setTrabajador(t);

            transformacionService.registrarTransformacion(transformacion);
            return "redirect:/web/transformaciones";

        } catch (RuntimeException e) {
            // Si falta stock u ocurre otro error de negocio
            model.addAttribute("error", e.getMessage());
            model.addAttribute("transformacion", transformacion);
            cargarListas(model);
            return "transformaciones/formularioTransformacion";
        }
    }

    // 4. VER DETALLE
    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Transformacion t = transformacionService.buscarPorId(id);
        if (t == null) {
            return "redirect:/web/transformaciones";
        }
        model.addAttribute("t", t);
        return "transformaciones/detalleTransformacion";
    }

    private void cargarListas(Model model) {
        // Necesitamos la lista de ORIGEN (Residuos) y DESTINO (Productos)
        model.addAttribute("residuos", materialService.listarPorTipo("RESIDUO"));
        model.addAttribute("productos", materialService.listarPorTipo("PRODUCTO"));
    }
}
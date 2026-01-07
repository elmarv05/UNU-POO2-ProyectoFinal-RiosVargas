package com.reciclaje.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.reciclaje.model.Compra;
import com.reciclaje.model.DetalleCompra;
import com.reciclaje.model.Material;
import com.reciclaje.model.Trabajador;
import com.reciclaje.service.CompraService;
import com.reciclaje.service.MaterialService;
import com.reciclaje.service.ProveedorService;
import com.reciclaje.service.TrabajadorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web/compras")
@SessionAttributes("compra")
public class CompraController {

    @Autowired
    private CompraService compraService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping
    public String listar(@RequestParam(required = false) Integer trabajadorId, Model model, SessionStatus status) {
        status.setComplete();

        List<Compra> lista;
        if (trabajadorId != null) {
            lista = compraService.listarPorTrabajador(trabajadorId);
            model.addAttribute("trabajadorSeleccionado", trabajadorId);
        } else {
            lista = compraService.listarCompras();
        }

        model.addAttribute("compras", lista);
        model.addAttribute("trabajadores", trabajadorService.listarTodos());
        return "compras/listaCompras";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        Compra compra = new Compra();
        compra.setTotal(0.0);
        compra.setDetalles(new ArrayList<>());

        model.addAttribute("compra", compra);
        cargarListas(model);
        return "compras/formularioCompra";
    }

    // En CompraController.java

    @PostMapping("/agregar-item")
    public String agregarItem(@ModelAttribute Compra compra,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false) Double cantidad,
            @RequestParam(required = false) Double precio,
            Model model) {

        // 1. Validación de seguridad
        if (materialId == null || cantidad == null || precio == null) {
            model.addAttribute("error", "Error: Seleccione un material, cantidad y precio validos.");
            cargarListas(model);
            return "compras/formularioCompra";
        }

        // 2. Lógica de Fusión (Merge)
        boolean existe = false;
        if (compra.getDetalles() == null)
            compra.setDetalles(new ArrayList<>());

        for (DetalleCompra det : compra.getDetalles()) {
            if (det.getMaterial().getId().equals(materialId)) {
                det.setCantidad(det.getCantidad() + cantidad);
                det.setSubtotal(det.getCantidad() * det.getPrecio());
                existe = true;
                break;
            }
        }

        if (!existe) {
            Material material = materialService.buscarPorId(materialId);
            DetalleCompra detalle = new DetalleCompra();
            detalle.setMaterial(material);
            detalle.setCantidad(cantidad);
            detalle.setPrecio(precio);
            detalle.setSubtotal(cantidad * precio);
            compra.agregarDetalle(detalle);
        }

        double sumaTotal = compra.getDetalles().stream().mapToDouble(DetalleCompra::getSubtotal).sum();
        compra.setTotal(sumaTotal);

        cargarListas(model);
        return "compras/formularioCompra";
    }

    @GetMapping("/eliminar-item/{index}")
    public String eliminarItem(@ModelAttribute Compra compra, @PathVariable int index, Model model) {
        if (index >= 0 && index < compra.getDetalles().size()) {
            compra.getDetalles().remove(index); // Quitamos de la lista

            double sumaTotal = compra.getDetalles().stream().mapToDouble(DetalleCompra::getSubtotal).sum();
            compra.setTotal(sumaTotal);
        }
        cargarListas(model);
        return "compras/formularioCompra";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Compra compra, HttpSession session, SessionStatus status) {
        Trabajador t = (Trabajador) session.getAttribute("usuarioLogueado");
        if (t == null) {
            return "redirect:/login";
        }
        compra.setTrabajador(t);

        compraService.guardarCompra(compra);

        status.setComplete();
        return "redirect:/web/compras";
    }

    private void cargarListas(Model model) {
        model.addAttribute("proveedores", proveedorService.listarActivos());
        model.addAttribute("materiales", materialService.listarActivosPorTipo("RESIDUO"));
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Compra compra = compraService.buscarPorId(id);
        if (compra == null) {
            return "redirect:/web/compras";
        }
        model.addAttribute("compra", compra);
        return "compras/detalleCompra"; // Archivo HTML que crearemos ahora
    }
}
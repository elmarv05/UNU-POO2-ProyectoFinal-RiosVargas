package com.reciclaje.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.reciclaje.model.Venta;
import com.reciclaje.model.DetalleVenta;
import com.reciclaje.model.Material;
import com.reciclaje.model.Trabajador;
import com.reciclaje.service.ClienteService;
import com.reciclaje.service.MaterialService;
import com.reciclaje.service.TrabajadorService;
import com.reciclaje.service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web/ventas")
@SessionAttributes("venta") // Mantenemos la venta en sesión mientras agregamos productos
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private TrabajadorService trabajadorService;

    // 1. LISTAR VENTAS (Historial)
    @GetMapping
    public String listar(Model model, SessionStatus status) {
        // Limpiamos la sesión por si quedó alguna venta a medias
        status.setComplete(); 
        
        model.addAttribute("ventas", ventaService.listarVentas());
        return "ventas/listaVentas"; // Vista que crearemos luego
    }

    // 2. FORMULARIO NUEVA VENTA
    @GetMapping("/nueva")
    public String nueva(Model model) {
        Venta venta = new Venta();
        venta.setTotal(0.0);
        venta.setDetalles(new ArrayList<>());
        
        model.addAttribute("venta", venta);
        cargarListas(model);
        return "ventas/formularioVenta"; // Vista que crearemos luego
    }

    // 3. AGREGAR ITEM AL CARRITO (Temporal en sesión)
    @PostMapping("/agregar-item")
    public String agregarItem(@ModelAttribute Venta venta, 
                              @RequestParam Integer materialId, 
                              @RequestParam Double cantidad, 
                              @RequestParam Double precio, // Precio de venta sugerido o modificado
                              Model model) {
        
        Material producto = materialService.buscarPorId(materialId);

        // Validación básica de stock antes de agregar a la lista visual
        if (producto.getStock() < cantidad) {
            model.addAttribute("error", "Stock insuficiente. Disponible: " + producto.getStock());
            cargarListas(model);
            return "ventas/formularioVenta";
        }

        DetalleVenta detalle = new DetalleVenta();
        detalle.setMaterial(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);
        detalle.setSubtotal(cantidad * precio);
    
        venta.agregarDetalle(detalle);
     
        // Recalcular Total
        double sumaTotal = venta.getDetalles().stream().mapToDouble(DetalleVenta::getSubtotal).sum();
        venta.setTotal(sumaTotal);
        
        cargarListas(model);
        return "ventas/formularioVenta";
    }

    // 4. ELIMINAR ITEM DEL CARRITO
    @GetMapping("/eliminar-item/{index}")
    public String eliminarItem(@ModelAttribute Venta venta, @PathVariable int index, Model model) {
        if (index >= 0 && index < venta.getDetalles().size()) {
            venta.getDetalles().remove(index);
            
            // Recalcular Total
            double sumaTotal = venta.getDetalles().stream().mapToDouble(DetalleVenta::getSubtotal).sum();
            venta.setTotal(sumaTotal);
        }
        cargarListas(model);
        return "ventas/formularioVenta";
    }

    // 5. GUARDAR VENTA (Persistir en BD y descontar Stock)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, HttpSession session, SessionStatus status, Model model) {
        try {
            // Asignar trabajador (En un caso real, se obtiene del usuario logueado en sesión)
            // Trabajador t = (Trabajador) session.getAttribute("usuarioLogueado");
            Trabajador t = trabajadorService.buscarPorId(1); // Temporal: Hardcodeamos al Admin
            venta.setTrabajador(t);
            
            ventaService.guardarVenta(venta);
            
            status.setComplete(); // Limpiar sesión
            return "redirect:/web/ventas";
            
        } catch (RuntimeException e) {
            // Si el servicio lanza error de stock, volvemos al formulario con el mensaje
            model.addAttribute("error", e.getMessage());
            cargarListas(model);
            return "ventas/formularioVenta";
        }
    }

    // 6. VER DETALLE
    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Venta venta = ventaService.buscarPorId(id);
        if (venta == null) {
            return "redirect:/web/ventas";
        }
        model.addAttribute("venta", venta);
        return "ventas/detalleVenta";
    }

    // Método auxiliar para cargar desplegables
    private void cargarListas(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        // CLAVE: Solo cargamos materiales que sean PRODUCTOS TERMINADOS
        model.addAttribute("productos", materialService.listarPorTipo("PRODUCTO"));
    }
}

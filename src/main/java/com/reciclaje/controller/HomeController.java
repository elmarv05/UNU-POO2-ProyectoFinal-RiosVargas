package com.reciclaje.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reciclaje.dto.IVentasPorMes;
import com.reciclaje.model.Trabajador;
import com.reciclaje.service.*; // Importamos TODOS los servicios

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    // INYECCIÓN DE SERVICIOS (Respetando arquitectura por capas)
    @Autowired private TrabajadorService trabajadorService;
    @Autowired private ClienteService clienteService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private MaterialService materialService;
    @Autowired private VentaService ventaService;
    @Autowired private CompraService compraService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Trabajador t = trabajadorService.validarCredenciales(username, password);

        if (t != null) {
            session.setAttribute("usuarioLogueado", t);
            return "redirect:/web/home"; 
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }
    
    // --- DASHBOARD ---
    @GetMapping("/web/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        // 1. Totales (Usando los métodos nuevos de los Servicios)
        model.addAttribute("cantClientes", clienteService.contarClientes());
        model.addAttribute("cantProveedores", proveedorService.contarProveedores());
        model.addAttribute("cantProductos", materialService.contarProductos());
        
        // 2. Finanzas
        Double totalVentas = ventaService.obtenerTotalVentas();
        Double totalCompras = compraService.obtenerTotalCompras();
        
        model.addAttribute("totalVentas", totalVentas != null ? totalVentas : 0.0);
        model.addAttribute("totalCompras", totalCompras != null ? totalCompras : 0.0);

        // 3. Alertas de Stock (Menos de 10)
        model.addAttribute("alertasStock", materialService.buscarStockBajo(10.0));
        
        // 4. Gráfico
        List<IVentasPorMes> datosGrafico = ventaService.obtenerReporteMensual();
        
        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();
        
        // Procesamos la lista para separarla en arrays para JS
        if(datosGrafico != null) {
            for(IVentasPorMes v : datosGrafico) {
                // Formato Mes/Año
                labels.add(v.getMes() + "/" + v.getAnio());
                data.add(v.getTotal());
            }
        }
        
        model.addAttribute("graficoLabels", labels);
        model.addAttribute("graficoData", data);

        return "home"; 
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
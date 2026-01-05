package com.reciclaje.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reciclaje.dto.IComprasPorMes;
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

        // 1. Totales y KPIs (Existente)
        model.addAttribute("cantClientes", clienteService.contarClientes());
        model.addAttribute("cantProveedores", proveedorService.contarProveedores());
        model.addAttribute("cantProductos", materialService.contarProductos());
        model.addAttribute("cantResiduos", materialService.contarResiduos());
        Double totalVentas = ventaService.obtenerTotalVentas();
        Double totalCompras = compraService.obtenerTotalCompras();
        model.addAttribute("totalVentas", totalVentas != null ? totalVentas : 0.0);
        model.addAttribute("totalCompras", totalCompras != null ? totalCompras : 0.0);
        
        model.addAttribute("alertasStock", materialService.buscarStockBajo(10.0));

        // 2. GRÁFICO DE VENTAS (Existente)
        List<IVentasPorMes> datosVentas = ventaService.obtenerReporteMensual();
        List<String> labelsVentas = new ArrayList<>();
        List<Double> dataVentas = new ArrayList<>();
        if(datosVentas != null) {
            for(IVentasPorMes v : datosVentas) {
                labelsVentas.add(v.getMes() + "/" + v.getAnio());
                dataVentas.add(v.getTotal());
            }
        }
        model.addAttribute("graficoLabels", labelsVentas);
        model.addAttribute("graficoData", dataVentas);

        // 3. GRÁFICO DE COMPRAS (¡NUEVO!)
        List<IComprasPorMes> datosCompras = compraService.obtenerReporteMensual();
        List<String> labelsCompras = new ArrayList<>();
        List<Double> dataCompras = new ArrayList<>();
        
        if(datosCompras != null) {
            for(IComprasPorMes c : datosCompras) {
                labelsCompras.add(c.getMes() + "/" + c.getAnio());
                dataCompras.add(c.getTotal());
            }
        }
        model.addAttribute("graficoComprasLabels", labelsCompras);
        model.addAttribute("graficoComprasData", dataCompras);

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
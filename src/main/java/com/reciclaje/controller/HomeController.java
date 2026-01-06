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
    @Autowired
    private TrabajadorService trabajadorService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private CompraService compraService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, @RequestParam String password, HttpSession session,
            Model model) {
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

        // 2. DATOS UNIFICADOS PARA EL GRÁFICO (Ingresos vs Egresos)
        List<IVentasPorMes> datosVentas = ventaService.obtenerReporteMensual();
        List<IComprasPorMes> datosCompras = compraService.obtenerReporteMensual();

        // Usamos un Map para alinear los datos por fecha (Mes/Año)
        // Clave: "Anio-Mes" (para ordenar), Valor: [Ventas, Compras]
        java.util.TreeMap<String, Double[]> balanceMap = new java.util.TreeMap<>();

        // Poblar Ventas
        if (datosVentas != null) {
            for (IVentasPorMes v : datosVentas) {
                // Formateamos la clave para que sea ordenable lexicográficamente: "YYYY-MM"
                // Asumiendo que getMes() devuelve 1-12. Rellenamos con 0 a la izquierda.
                String key = String.format("%04d-%02d", v.getAnio(), v.getMes());
                balanceMap.putIfAbsent(key, new Double[] { 0.0, 0.0 });
                balanceMap.get(key)[0] = v.getTotal();
            }
        }

        // Poblar Compras
        if (datosCompras != null) {
            for (IComprasPorMes c : datosCompras) {
                String key = String.format("%04d-%02d", c.getAnio(), c.getMes());
                balanceMap.putIfAbsent(key, new Double[] { 0.0, 0.0 });
                balanceMap.get(key)[1] = c.getTotal();
            }
        }

        // Separar en listas alineadas para el frontend
        List<String> labels = new ArrayList<>();
        List<Double> dataVentasList = new ArrayList<>();
        List<Double> dataComprasList = new ArrayList<>();

        for (java.util.Map.Entry<String, Double[]> entry : balanceMap.entrySet()) {
            // Convertir clave "YYYY-MM" a etiqueta amigable "MM/YYYY" para la vista
            String[] parts = entry.getKey().split("-");
            String mes = parts[1]; // ya viene con 0 si es necesario, o podemos parsear integer
            String anio = parts[0];
            // Quitar 0 delante si se prefiere formato "1/2024"
            try {
                int mesInt = Integer.parseInt(mes);
                labels.add(mesInt + "/" + anio);
            } catch (NumberFormatException e) {
                labels.add(entry.getKey());
            }

            dataVentasList.add(entry.getValue()[0]);
            dataComprasList.add(entry.getValue()[1]);
        }

        model.addAttribute("graficoLabels", labels);
        model.addAttribute("graficoDataVentas", dataVentasList);
        model.addAttribute("graficoDataCompras", dataComprasList);

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
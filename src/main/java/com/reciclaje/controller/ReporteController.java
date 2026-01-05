package com.reciclaje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reciclaje.service.*; // Importamos todos los servicios
// Puedes importar los modelos si es necesario, o usar List<?> genérico

@Controller
@RequestMapping("/web/reportes")
public class ReporteController {

    @Autowired private JasperService jasperService;
    @Autowired private MaterialService materialService;
    @Autowired private ClienteService clienteService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private CompraService compraService;
    @Autowired private VentaService ventaService;
    @Autowired private TransformacionService transformacionService;
    @Autowired private TrabajadorService trabajadorService;
    @Autowired private CategoriaService categoriaService;

    // --- MATERIALES (Ya los tenías, actualizados al método genérico) ---
    
    @GetMapping("/residuos")
    public ResponseEntity<byte[]> reporteResiduos() {
        return generarPdf(materialService.listarResiduos(), "inventarioResiduos", "ReporteResiduos.pdf");
    }
    
    @GetMapping("/productos")
    public ResponseEntity<byte[]> reporteProductos() {
        return generarPdf(materialService.listarProductos(), "inventarioProductos", "ReporteProductos.pdf");
    }

    // --- NUEVOS REPORTES ---

    @GetMapping("/clientes")
    public ResponseEntity<byte[]> reporteClientes() {
        // Nombre del jrxml: listaClientes.jrxml
        return generarPdf(clienteService.listarTodos(), "listaClientes", "ReporteClientes.pdf");
    }

    @GetMapping("/proveedores")
    public ResponseEntity<byte[]> reporteProveedores() {
        // Nombre del jrxml: listaProveedores.jrxml
        return generarPdf(proveedorService.listarTodos(), "listaProveedores", "ReporteProveedores.pdf");
    }

    @GetMapping("/compras")
    public ResponseEntity<byte[]> reporteCompras() {
        // Nombre del jrxml: historialCompras.jrxml
        return generarPdf(compraService.listarCompras(), "historialCompras", "ReporteCompras.pdf");
    }

    @GetMapping("/ventas")
    public ResponseEntity<byte[]> reporteVentas() {
        // Nombre del jrxml: historialVentas.jrxml
        return generarPdf(ventaService.listarVentas(), "historialVentas", "ReporteVentas.pdf");
    }
    
    @GetMapping("/transformaciones")
    public ResponseEntity<byte[]> reporteProduccion() {
        // Nombre del jrxml: historialTransformaciones.jrxml
        return generarPdf(transformacionService.listarTodas(), "historialTransformaciones", "ReporteProduccion.pdf");
    }

    @GetMapping("/usuarios")
    public ResponseEntity<byte[]> reporteUsuarios() {
        // Nombre del jrxml: listaTrabajadores.jrxml
        return generarPdf(trabajadorService.listarTodos(), "listaTrabajadores", "ReportePersonal.pdf");
    }
    

    // --- MÉTODO PRIVADO AUXILIAR PARA NO REPETIR CÓDIGO TRY-CATCH ---
    private ResponseEntity<byte[]> generarPdf(List<?> datos, String nombreJrxml, String nombreSalida) {
        try {
            byte[] reporte = jasperService.generarReporte(datos, nombreJrxml);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreSalida)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reporte);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
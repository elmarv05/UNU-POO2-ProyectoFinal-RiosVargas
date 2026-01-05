package com.reciclaje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reciclaje.service.JasperService;
import com.reciclaje.service.MaterialService;
import com.reciclaje.model.Material;
@Controller
@RequestMapping("/web/reportes")
public class ReporteController {

    @Autowired
    private JasperService jasperService;
    
    @Autowired
    private MaterialService materialService;

    @GetMapping("/residuos")
    public ResponseEntity<byte[]> reporteResiduos() {
    	try {
            // 1. Obtener datos reales de la BD
            List<Material> res = materialService.listarResiduos(); 
            
            // 2. Generar PDF
            byte[] reporte = jasperService.generarReporteInventario(res, "Residuos");
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=InventarioResiduos.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reporte);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/productos")
    public ResponseEntity<byte[]> reporteProductos() {
    	try {
            // 1. Obtener datos reales de la BD
            List<Material> pro = materialService.listarProductos(); 
            
            // 2. Generar PDF
            byte[] reporte = jasperService.generarReporteInventario(pro, "Productos");
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=InventarioProductos.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reporte);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
}
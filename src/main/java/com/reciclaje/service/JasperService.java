package com.reciclaje.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;

import com.reciclaje.model.Material;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperService {

    public byte[] generarReporteInventario(List<Material> listaMateriales, String tipo) throws IOException, JRException {
        
    	
    	ClassPathResource imageResource = new ClassPathResource("static/img/logo.png");
    	InputStream imageStream = imageResource.getInputStream();
    	
    	
    	File file = ResourceUtils.getFile("classpath:reports/inventario"+ tipo +".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        
        // AQUÍ ESTÁ LA CLAVE: Pasamos la lista real de objetos Material
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaMateriales);
        
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("autor", "Recicladora Carlitos");
        parametros.put("logo_input_stream", imageStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    
}
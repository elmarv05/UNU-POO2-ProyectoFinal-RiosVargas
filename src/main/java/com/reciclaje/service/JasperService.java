package com.reciclaje.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperService {

    // Método GENÉRICO para cualquier tipo de lista (Clientes, Ventas, etc.)
    public byte[] generarReporte(List<?> listaDatos, String nombreArchivoJrxml) throws IOException, JRException {
        
        // 1. Cargar logo
        ClassPathResource imageResource = new ClassPathResource("static/img/logo.png");
        InputStream imageStream = imageResource.getInputStream();

        // 2. Cargar archivo .jrxml (Usando InputStream es más seguro para JARs)
        ClassPathResource reportResource = new ClassPathResource("reports/" + nombreArchivoJrxml + ".jrxml");
        InputStream reportStream = reportResource.getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 3. Crear el DataSource con la lista genérica
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDatos);

        // 4. Parámetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("autor", "Recicladora Carlitos");
        parametros.put("logo_input_stream", imageStream);

        // 5. Llenar y exportar
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
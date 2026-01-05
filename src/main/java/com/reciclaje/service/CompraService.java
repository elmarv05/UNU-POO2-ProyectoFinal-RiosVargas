package com.reciclaje.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reciclaje.dto.IComprasPorMes;
import com.reciclaje.model.Compra;
import com.reciclaje.model.DetalleCompra;
import com.reciclaje.model.Material;
import com.reciclaje.repository.CompraRepository;
import com.reciclaje.repository.MaterialRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Compra> listarCompras() {
        return compraRepository.findAllByOrderByFechaDesc();
    }

    public Compra buscarPorId(Integer id) {
        return compraRepository.findById(id).orElse(null);
    }

    
 // En CompraService

    public Double obtenerTotalCompras() {
        return compraRepository.sumarComprasTotales();
    }
    
    // @Transactional es como begin - end
    @Transactional
    public Compra guardarCompra(Compra compra) {
                
        if (compra.getFecha() == null) {
            compra.setFecha(LocalDateTime.now());
        }
        
        if (compra.getCodigo() == null) {
            compra.setCodigo("CP-" + System.currentTimeMillis());
        }

       
        for (DetalleCompra detalle : compra.getDetalles()) {
            
            Material material = materialRepository.findById(detalle.getMaterial().getId())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));

            Double nuevoStock = material.getStock() + detalle.getCantidad();
            material.setStock(nuevoStock);

            materialRepository.save(material);
        }
        return compraRepository.save(compra);
    }
    
    public List<IComprasPorMes> obtenerReporteMensual() {
        return compraRepository.obtenerComprasPorMes();
    }
    
}
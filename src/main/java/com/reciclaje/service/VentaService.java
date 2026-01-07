package com.reciclaje.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reciclaje.dto.IVentasPorMes;
import com.reciclaje.model.DetalleVenta;
import com.reciclaje.model.Material;
import com.reciclaje.model.Venta;
import com.reciclaje.repository.MaterialRepository;
import com.reciclaje.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAllByOrderByFechaDesc();
    }

    public List<Venta> listarPorTrabajador(Integer trabajadorId) {
        return ventaRepository.findByTrabajadorIdOrderByFechaDesc(trabajadorId);
    }

    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    /**
     * Lógica transaccional de venta:
     * 1. Genera código y fecha.
     * 2. Recorre los items.
     * 3. Verifica si hay stock suficiente.
     * 4. DESCUENTA el stock.
     * 5. Guarda todo.
     */

    // En VentaService

    // Total histórico
    public Double obtenerTotalVentas() {
        return ventaRepository.sumarVentasTotales();
    }

    // Datos para el Gráfico
    public List<IVentasPorMes> obtenerReporteMensual() {
        return ventaRepository.obtenerVentasPorMes();
    }

    @Transactional
    public void guardarVenta(Venta venta) {

        // 1. Datos automáticos
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDateTime.now());
        }

        // Generador simple de código (Mejorable en el futuro)
        if (venta.getCodigo() == null) {
            venta.setCodigo("VN-" + System.currentTimeMillis());
        }

        // 2. Procesar cada detalle
        for (DetalleVenta detalle : venta.getDetalles()) {

            // Buscamos el producto en la BD para asegurar datos frescos
            Material producto = materialRepository.findById(detalle.getMaterial().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // 3. Validación de Stock (Regla de Negocio Crítica)
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // 4. Actualizar inventario (RESTA)
            Double nuevoStock = producto.getStock() - detalle.getCantidad();
            producto.setStock(nuevoStock);

            // Guardamos el material actualizado
            materialRepository.save(producto);
        }

        // 5. Guardar la venta (Cascade guardará los detalles)
        ventaRepository.save(venta);
    }
}
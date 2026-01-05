package com.reciclaje.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reciclaje.model.Material;
import com.reciclaje.model.Transformacion;
import com.reciclaje.repository.MaterialRepository;
import com.reciclaje.repository.TransformacionRepository;

@Service
public class TransformacionService {

    @Autowired
    private TransformacionRepository transformacionRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Transformacion> listarTodas() {
        return transformacionRepository.findAllByOrderByFechaDesc();
    }

    public Transformacion buscarPorId(Integer id) {
        return transformacionRepository.findById(id).orElse(null);
    }

    /**
     * Lógica Crítica:
     * 1. Verifica stock del residuo.
     * 2. RESTA stock del residuo (Materia Prima).
     * 3. SUMA stock del producto (Producto Terminado).
     * 4. Registra la operación.
     */
    @Transactional
    public void registrarTransformacion(Transformacion t) {
        
        // 1. Buscar entidades
        Material origen = materialRepository.findById(t.getMaterialOrigen().getId())
                .orElseThrow(() -> new RuntimeException("Material origen no encontrado"));
        Material destino = materialRepository.findById(t.getMaterialDestino().getId())
                .orElseThrow(() -> new RuntimeException("Material destino no encontrado"));

        // 2. REGLA DE NEGOCIO: Recalcular la cantidad origen para seguridad
        // Si el destino tiene un factor de conversión definido (ej. 10.0), lo usamos.
        // Si es null o 0, asumimos 1.0
        Double factor = (destino.getFactorConversion() != null && destino.getFactorConversion() > 0) 
                      ? destino.getFactorConversion() 
                      : 1.0;
        
        // Calculamos cuánto debería ser el consumo real
        Double calc = t.getCantidadDestino() * factor;
        
        // Opcional: Forzar el valor calculado (sobrescribir lo que envió el form)
        t.setCantidadOrigen(calc);

        // 3. Validar Stock
        if (origen.getStock() < t.getCantidadOrigen()) {
            throw new RuntimeException("Stock insuficiente. Se requieren " + t.getCantidadOrigen() + 
                                       " " + origen.getUnidad() + " pero solo hay " + origen.getStock());
        }

        // 4. Actualizar stocks
        origen.setStock(origen.getStock() - t.getCantidadOrigen());
        destino.setStock(destino.getStock() + t.getCantidadDestino());

        materialRepository.save(origen);
        materialRepository.save(destino);

        // 5. Guardar
        if (t.getFecha() == null) t.setFecha(LocalDateTime.now());
        if (t.getCodigo() == null) t.setCodigo("TR-" + System.currentTimeMillis());
        
        transformacionRepository.save(t);
    }
}
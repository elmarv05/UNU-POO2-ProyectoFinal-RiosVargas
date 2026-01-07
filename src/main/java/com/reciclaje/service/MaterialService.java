package com.reciclaje.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reciclaje.model.Material;
import com.reciclaje.repository.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> listarTodos() {
        return materialRepository.findAll();
    }

    public List<Material> listarActivos() {
        return materialRepository.findByActivoTrue();
    }

    public List<Material> listarPorTipo(String tipo) {
        return materialRepository.findByTipo(tipo);
    }

    public List<Material> listarActivosPorTipo(String tipo) {
        return materialRepository.findByActivoTrueAndTipo(tipo);
    }

    // Filtros por Categoría
    public List<Material> listarPorTipoYCategoria(String tipo, Integer categoriaId) {
        return materialRepository.findByTipoAndCategoriaId(tipo, categoriaId);
    }

    public List<Material> listarActivosPorTipoYCategoria(String tipo, Integer categoriaId) {
        return materialRepository.findByActivoTrueAndTipoAndCategoriaId(tipo, categoriaId);
    }

    public List<Material> listarResiduos() {
        return materialRepository.findByTipo("RESIDUO");
    }

    public List<Material> listarProductos() {
        return materialRepository.findByTipo("PRODUCTO");
    }

    public void guardar(Material material) {
        // Lógica de seguridad: Evitar NullPointerExceptions en precios y stock
        if (material.getId() == null && material.getStock() == null) {
            material.setStock(0.0);
        }
        if (material.getPrecio() == null)
            material.setPrecio(0.0);

        materialRepository.save(material);
    }

    // En MaterialService

    // 1. Contar solo productos (int)
    public int contarProductos() {
        return materialRepository.countByTipo("PRODUCTO");
    }

    public int contarResiduos() {
        return materialRepository.countByTipo("RESIDUO");
    }

    // 2. Alerta de Stock
    public List<Material> buscarStockBajo(Double min) {
        return materialRepository.findByStockLessThan(min);
    }

    public Material buscarPorId(Integer id) {
        return materialRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        Material material = buscarPorId(id);
        if (material != null) {
            material.setActivo(false);
            materialRepository.save(material);
        }
    }
}
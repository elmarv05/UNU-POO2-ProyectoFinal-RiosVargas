package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
    
    List<Material> findByTipo(String tipo);
    
    int countByTipo(String tipo);
    
    List<Material> findByStockLessThan(Double cantidad);
}
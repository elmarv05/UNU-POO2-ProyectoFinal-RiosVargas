package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
    // ESTA ES LA CLAVE: Nos permite pedir solo "RESIDUO" o solo "PRODUCTO"
    List<Material> findByTipo(String tipo);
}
package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Transformacion;

@Repository
public interface TransformacionRepository extends JpaRepository<Transformacion, Integer> {
    
    // Historial ordenado por fecha descendente
    List<Transformacion> findAllByOrderByFechaDesc();
}
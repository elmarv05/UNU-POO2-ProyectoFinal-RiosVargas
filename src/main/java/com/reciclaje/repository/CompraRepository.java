package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    
    List<Compra> findAllByOrderByFechaDesc();
}
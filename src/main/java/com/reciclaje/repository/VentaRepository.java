package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // Para mostrar el historial ordenado por la más reciente
    List<Venta> findAllByOrderByFechaDesc();
}
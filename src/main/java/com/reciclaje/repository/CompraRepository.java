package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Compra;
import com.reciclaje.dto.IComprasPorMes;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    List<Compra> findAllByOrderByFechaDesc();

    List<Compra> findByTrabajadorIdOrderByFechaDesc(Integer trabajadorId);

    @Query(value = "CALL sp_sumar_compras_totales()", nativeQuery = true)
    Double sumarComprasTotales();

    @Query(value = "CALL sp_obtener_compras_por_mes()", nativeQuery = true)
    List<IComprasPorMes> obtenerComprasPorMes();
}
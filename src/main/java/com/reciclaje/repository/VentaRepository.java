package com.reciclaje.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Venta;
import com.reciclaje.dto.IVentasPorMes;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // Para mostrar el historial ordenado por la más reciente
    List<Venta> findAllByOrderByFechaDesc();

    @Query(value = "CALL sp_sumar_ventas_totales()", nativeQuery = true)
    Double sumarVentasTotales();

    // CONSULTA JPQL AVANZADA PARA GRÁFICOS
    // Agrupa por Año y Mes, y suma los totales
    @Query(value = "CALL sp_obtener_ventas_por_mes()", nativeQuery = true)
    List<IVentasPorMes> obtenerVentasPorMes();
}
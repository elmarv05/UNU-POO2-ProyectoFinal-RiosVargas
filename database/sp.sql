DELIMITER //
CREATE PROCEDURE sp_obtener_compras_por_mes()
BEGIN
    SELECT 
        MONTH(fecha_compra) as mes,
        YEAR(fecha_compra) as anio,
        SUM(total) as total
    FROM compras
    GROUP BY YEAR(fecha_compra), MONTH(fecha_compra)
    ORDER BY YEAR(fecha_compra) ASC, MONTH(fecha_compra) ASC
    LIMIT 12; -- Últimos 12 meses
END;
DELIMITER;

DELIMITER //
CREATE  PROCEDURE sp_obtener_ventas_por_mes()
BEGIN
    
    SELECT 
        YEAR(fecha_venta) as anio, 
        MONTH(fecha_venta) as mes, 
        SUM(total) as total 
    FROM ventas 
    GROUP BY YEAR(fecha_venta), MONTH(fecha_venta) 
    ORDER BY YEAR(fecha_venta), MONTH(fecha_venta);
END;
DELIMITER;

DELIMITER //
CREATE PROCEDURE sp_sumar_compras_totales()
BEGIN
    
    SELECT IFNULL(SUM(total), 0) FROM compras;
END;
DELIMITER;

DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_sumar_ventas_totales`()
BEGIN
   
    SELECT IFNULL(SUM(total), 0) FROM ventas;
END;
DELIMITER;


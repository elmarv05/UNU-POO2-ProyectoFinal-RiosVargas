package com.reciclaje.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasPorMesDTO {
    private Integer mes;   // 1, 2, 3...
    private Integer anio;  // 2025
    private Double total;  // Suma de ventas
}
package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transformaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transformacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_operacion", unique = true, length = 20)
    private String codigo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 255)
    private String descripcion;

    // INPUT (Residuo)
    @ManyToOne
    @JoinColumn(name = "material_origen_id", nullable = false)
    private Material materialOrigen;

    @Column(name = "cantidad_origen", nullable = false)
    private Double cantidadOrigen; // KG consumidos

    // OUTPUT (Producto)
    @ManyToOne
    @JoinColumn(name = "material_destino_id", nullable = false)
    private Material materialDestino;

    @Column(name = "cantidad_destino", nullable = false)
    private Double cantidadDestino; // Unidades producidas

    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador;
}
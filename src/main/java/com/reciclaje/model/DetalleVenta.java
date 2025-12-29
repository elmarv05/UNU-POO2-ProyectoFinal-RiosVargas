package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double cantidad; 

    @Column(nullable = false)
    private Double precio; // Precio unitario al momento de la venta

    @Column(nullable = false)
    private Double subtotal; 

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    @ToString.Exclude
    private Venta venta;
    
    // Conectamos con Material. Aquí seleccionaremos solo los que tengan tipo="PRODUCTO"
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}
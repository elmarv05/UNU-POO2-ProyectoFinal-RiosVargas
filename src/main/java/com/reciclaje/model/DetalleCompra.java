package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double cantidad; 

    @Column(nullable = false)
    private Double precio; 

    @Column(nullable = false)
    private Double subtotal; 

    
    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    @ToString.Exclude 
    private Compra compra;
    
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}
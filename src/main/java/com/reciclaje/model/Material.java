package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materiales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre; 

    @Column(nullable = false, length = 20)
    private String tipo; 

    
    @Column(name = "precio_compra")
    private Double precioCompra; 

    
    @Column(name = "precio_venta")
    private Double precioVenta; 

    @Column(nullable = false)
    private Double stock = 0.0; 

    @Column(length = 20)
    private String unidad; 

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materiales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Column(nullable = false)
    private boolean activo = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false)
    private Double precio = 0.0;

    @Column(nullable = false)
    private Double stock = 0.0;

    @Column(name = "factor_conversion")
    private Double factorConversion;

    @Column(length = 20)
    private String unidad;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
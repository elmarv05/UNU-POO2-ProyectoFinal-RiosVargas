package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre; 

    @Column(unique = true, nullable = false, length = 15)
    private String documento; 

    @Column(length = 100)
    private String email;

    @Column(length = 15)
    private String telefono;
    
    @Column(length = 200)
    private String direccion;

}
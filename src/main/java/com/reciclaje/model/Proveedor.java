package com.reciclaje.model;

import java.util.ArrayList;
import java.util.List;

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
    private String razonSocial; 

    @Column(unique = true, nullable = false, length = 15)
    private String documento; 

    @Column(length = 100)
    private String email;

    @Column(length = 15)
    private String telefono;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(nullable = false)
    private boolean activo = true; // Por defecto nace activo
    
 // Agrega esto en tu clase Proveedor
    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    @ToString.Exclude // ¡IMPORTANTE!
    private List<Compra> historialCompras=new ArrayList<>();

}
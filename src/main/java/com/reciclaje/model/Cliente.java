package com.reciclaje.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String razonSocial;

    @Column(unique = true, nullable = false, length = 11) 
    private String rucDni;

    @Column(length = 200)
    private String direccion;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String email;
    
    @Column(nullable = false)
    private boolean activo = true; // Por defecto nace activo
    
 // Agrega esto en tu clase Cliente
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @ToString.Exclude // ¡IMPORTANTE! Evita bucle infinito al imprimir en consola
    private List<Venta> historialVenta = new ArrayList<>();
}
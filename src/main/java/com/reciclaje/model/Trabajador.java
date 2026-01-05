package com.reciclaje.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trabajadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;
    
    @Column(unique = true, length = 8) 
    private String dni;
    
    @Column(length = 15)
    private String telefono;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255) 
    private String pass;

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
 // 1. Historial de Compras registradas por este trabajador
    @OneToMany(mappedBy = "trabajador", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Compra> historialCompras = new ArrayList<>();

    // 2. Historial de Ventas realizadas por este trabajador
    @OneToMany(mappedBy = "trabajador", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Venta> historialVentas = new ArrayList<>();

    // 3. Historial de Transformaciones (Producción) hechas por este trabajador
    @OneToMany(mappedBy = "trabajador", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Transformacion> historialTransformaciones = new ArrayList<>();
}
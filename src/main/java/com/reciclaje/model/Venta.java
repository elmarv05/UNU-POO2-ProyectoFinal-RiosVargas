package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    
    @Column(name = "fecha_venta", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha; 

    // Código único de la boleta/factura (ej: VN-123456)
    @Column(name = "codigo_operacion", unique = true, length = 20)
    private String codigo; 

    @Column(nullable = false)
    private Double total;

    @Column(length = 255)
    private String observaciones;

    // Relación: Una venta pertenece a un Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relación: Una venta es registrada por un Trabajador
    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador;

    // Relación: Una venta tiene muchos detalles
    // CascadeType.ALL permite guardar los detalles automáticamente al guardar la venta
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    // Método helper para mantener la coherencia de la relación bidireccional
    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        detalle.setVenta(this);
    }
}
package com.reciclaje.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String nombre; 
   
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval=true)
    @ToString.Exclude
    @JsonIgnore
    private List<Trabajador> trabajadores;
}
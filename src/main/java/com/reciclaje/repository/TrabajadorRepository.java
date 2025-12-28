package com.reciclaje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
    
    Trabajador findByUsername(String username);
    
    Trabajador findByDni(String dni);
}
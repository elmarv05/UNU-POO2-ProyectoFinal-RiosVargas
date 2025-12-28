package com.reciclaje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
   Rol findByNombre(String nombre);
   
}
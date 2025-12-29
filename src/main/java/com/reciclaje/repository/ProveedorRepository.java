package com.reciclaje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reciclaje.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    // Para validar que no registremos al mismo proveedor dos veces
    Proveedor findByDocumento(String documento);
   List<Proveedor> findByActivoTrue();
}
package com.reciclaje.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reciclaje.model.Rol;
import com.reciclaje.model.Trabajador;
import com.reciclaje.repository.RolRepository;
import com.reciclaje.repository.TrabajadorRepository;

@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;
    
    @Autowired
    private RolRepository rolRepository;

    public List<Trabajador> listarTodos() {
        return trabajadorRepository.findAll();
    }

    public void guardar(Trabajador t) {        
        trabajadorRepository.save(t);
    }

    public Trabajador buscarPorId(Integer id) {
        return trabajadorRepository.findById(id).orElse(null);
    }

 // En TrabajadorService.java
    public void eliminar(Integer id) {
        Trabajador t = buscarPorId(id);
        if (t != null) {
            t.setActivo(false); // Borrado lógico
            trabajadorRepository.save(t);
        }
    }
    
   
    public Trabajador validarCredenciales(String nombre, String pass) {
        Trabajador t = trabajadorRepository.findByUsername(nombre);
        
        if (t!=null&&t.getPassword().equals(pass)&&t.isActivo()) {
            return t;
        }
        return null; 
    }
    
    
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }
}
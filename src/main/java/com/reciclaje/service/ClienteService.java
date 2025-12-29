package com.reciclaje.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reciclaje.model.Cliente;
import com.reciclaje.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public List<Cliente> listarActivos() {
        return clienteRepository.findByActivoTrue();
    }
    
    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        // En lugar de clienteRepository.deleteById(id);
        
        Cliente c = buscarPorId(id);
        if (c != null) {
            c.setActivo(false); // "Apagamos" el cliente
            clienteRepository.save(c); // Guardamos el cambio de estado
        }
    }
}
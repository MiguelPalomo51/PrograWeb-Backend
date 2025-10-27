package com.example.demo.Service;

import com.example.demo.Entity.Pista;
import com.example.demo.Repository.PistaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PistaService {

    private final PistaRepository repository;

    public PistaService(PistaRepository repository) {
        this.repository = repository;
    }

    public List<Pista> listarTodas() {
        return repository.findAll();
    }

    public Optional<Pista> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Crea una nueva pista.
     * Si ya existe una con el mismo nombre, lanza una excepción.
     */
    public Pista crear(Pista pista) {
        // Verificar si ya existe una pista con ese nombre
        Optional<Pista> existente = repository.findByNombre(pista.getNombre());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una pista con el nombre: " + pista.getNombre());
        }
        
        // Asegurar que no tenga ID para forzar creación
        pista.setId(null);
        return repository.save(pista);
    }

    /**
     * Actualiza una pista existente por ID.
     */
    public Pista actualizar(Long id, Pista pista) {
        Pista existente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pista no encontrada con id: " + id));
        
        // Actualizar campos
        existente.setNombre(pista.getNombre());
        existente.setFilas(pista.getFilas());
        existente.setColumnas(pista.getColumnas());
        existente.setGrid(pista.getGrid());
        
        return repository.save(existente);
    }

    /**
     * Método legacy - mantener para compatibilidad si es necesario
     * Pero es mejor usar crear() o actualizar() directamente
     */
    public Pista guardar(Pista pista) {
        if (pista.getId() == null) {
            // Si no tiene ID, es una creación
            return crear(pista);
        } else {
            // Si tiene ID, es una actualización
            return actualizar(pista.getId(), pista);
        }
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
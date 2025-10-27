package com.example.demo.Repository;

import com.example.demo.Entity.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PistaRepository extends JpaRepository<Pista, Long> {
    Optional<Pista> findByNombre(String nombre);
}

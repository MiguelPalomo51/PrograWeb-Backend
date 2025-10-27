package com.example.demo.Controller;

import com.example.demo.Entity.Pista;
import com.example.demo.Service.PistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pistas")
@CrossOrigin(origins = "http://localhost:4200")
public class PistaController {

    private final PistaService service;

    public PistaController(PistaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pista> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pista> obtenerPorId(@PathVariable Long id) {
        Optional<Pista> pista = service.obtenerPorId(id);
        return pista.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Pista pista) {
        try {
            Pista nuevaPista = service.crear(pista);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pista pista) {
        try {
            Pista pistaActualizada = service.actualizar(id, pista);
            return ResponseEntity.ok(pistaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
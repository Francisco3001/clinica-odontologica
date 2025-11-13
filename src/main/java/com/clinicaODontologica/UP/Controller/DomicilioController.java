package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.service.DomicilioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Sin tecnologia de vista
@RequestMapping("/api/domicilio")
public class DomicilioController {

    private final DomicilioService domicilioService;

    public DomicilioController(DomicilioService domicilioService) {
        this.domicilioService = domicilioService;
    }

    @PostMapping
    public ResponseEntity<Domicilio> guardar(@RequestBody Domicilio domicilio) {
        return ResponseEntity.ok(domicilioService.guardar(domicilio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarPorId(@PathVariable Long id) {
        Domicilio domicilio = domicilioService.buscar(id);
        return domicilio != null ? ResponseEntity.ok(domicilio) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Domicilio>> buscarTodos() {
        return ResponseEntity.ok(domicilioService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Domicilio> actualizar(@RequestBody Domicilio domicilio) {

        if (domicilio.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(domicilioService.actualizar(domicilio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        Domicilio domicilio = domicilioService.buscar(id);
        if (domicilio == null) {
            return ResponseEntity.notFound().build();
        }

        domicilioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

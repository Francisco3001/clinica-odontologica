package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turno")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        return ResponseEntity.ok(turnoService.guardar(turno));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Long id) {
        Turno turno = turnoService.buscar(id);
        return turno != null ? ResponseEntity.ok(turno) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos() {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno) {
        if (turno.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(turnoService.actualizar(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Turno turno = turnoService.buscar(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

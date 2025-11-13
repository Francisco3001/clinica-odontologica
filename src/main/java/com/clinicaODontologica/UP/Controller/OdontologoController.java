package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.service.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/odontologo")
public class OdontologoController {

    private final OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardar(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Long id) {
        Odontologo odontologo = odontologoService.buscar(id);
        return odontologo != null ? ResponseEntity.ok(odontologo) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos() {
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) {
        if (odontologo.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(odontologoService.actualizar(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Odontologo odontologo = odontologoService.buscar(id);
        if (odontologo == null) {
            return ResponseEntity.notFound().build();
        }
        odontologoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

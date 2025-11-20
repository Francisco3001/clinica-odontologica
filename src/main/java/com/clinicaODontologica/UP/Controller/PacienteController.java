package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.PacienteRequestDTO;
import com.clinicaODontologica.UP.dto.PacienteResponseDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.service.DomicilioService;
import com.clinicaODontologica.UP.service.PacienteService;

import com.clinicaODontologica.UP.mapper.PacienteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {

    private final PacienteService pacienteService;
    private final DomicilioService domicilioService;

    public PacienteController(PacienteService pacienteService, DomicilioService domicilioService) {
        this.pacienteService = pacienteService;
        this.domicilioService = domicilioService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> guardar(@RequestBody PacienteRequestDTO dto) {
        Paciente paciente = PacienteMapper.toEntityFromRequest(dto);

        Domicilio domicilio = domicilioService.buscar(dto.domicilioId);
        paciente.setDomicilio(domicilio);

        Paciente guardado = pacienteService.guardar(paciente);
        return ResponseEntity.ok(PacienteMapper.toResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscar(id);
        return paciente != null ?
                ResponseEntity.ok(PacienteMapper.toResponseDTO(paciente)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> buscarTodos() {
        List<PacienteResponseDTO> respuesta = pacienteService.buscarTodos()
                .stream()
                .map(PacienteMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO dto) {

        Paciente paciente = pacienteService.buscar(id);
        if (paciente == null) return ResponseEntity.notFound().build();

        paciente.setNombre(dto.nombre);
        paciente.setApellido(dto.apellido);
        paciente.setEmail(dto.email);
        paciente.setFechaIngreso(java.time.LocalDate.parse(dto.fechaIngreso));
        paciente.setNumeroContacto(dto.numeroContacto);

        Domicilio domicilio = domicilioService.buscar(dto.domicilioId);
        paciente.setDomicilio(domicilio);

        Paciente actualizado = pacienteService.actualizar(paciente);
        return ResponseEntity.ok(PacienteMapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscar(id);
        if (paciente == null) return ResponseEntity.notFound().build();

        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

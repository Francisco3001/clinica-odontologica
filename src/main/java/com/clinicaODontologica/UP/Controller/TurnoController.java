package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.TurnoRequestDTO;
import com.clinicaODontologica.UP.dto.TurnoResponseDTO;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.service.OdontologoService;
import com.clinicaODontologica.UP.service.PacienteService;
import com.clinicaODontologica.UP.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/turno")
public class TurnoController {

    private final TurnoService turnoService;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDTO> guardar(@RequestBody TurnoRequestDTO dto) {

        Paciente paciente = pacienteService.buscar(dto.pacienteId);
        Odontologo odontologo = odontologoService.buscar(dto.odontologoId);

        Turno turno = new Turno();
        turno.setFecha(LocalDate.parse(dto.fecha));
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        Turno guardado = turnoService.guardar(turno);
        return ResponseEntity.ok(toResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarPorId(@PathVariable Long id) {
        Turno turno = turnoService.buscar(id);
        return turno != null ? ResponseEntity.ok(toResponseDTO(turno)) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> buscarTodos() {
        List<TurnoResponseDTO> lista = turnoService.buscarTodos()
                .stream()
                .map(this::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> actualizar(@PathVariable Long id, @RequestBody TurnoRequestDTO dto) {
        Turno turno = turnoService.buscar(id);
        if (turno == null) return ResponseEntity.notFound().build();

        Paciente paciente = pacienteService.buscar(dto.pacienteId);
        Odontologo odontologo = odontologoService.buscar(dto.odontologoId);

        turno.setFecha(LocalDate.parse(dto.fecha));
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        Turno actualizado = turnoService.actualizar(turno);
        return ResponseEntity.ok(toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Turno turno = turnoService.buscar(id);
        if (turno == null) return ResponseEntity.notFound().build();

        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private TurnoResponseDTO toResponseDTO(Turno turno) {
        TurnoResponseDTO dto = new TurnoResponseDTO();
        dto.id = turno.getId();
        dto.fecha = turno.getFecha().toString();
        dto.pacienteId = turno.getPaciente().getId();
        dto.odontologoId = turno.getOdontologo().getId();
        return dto;
    }
}

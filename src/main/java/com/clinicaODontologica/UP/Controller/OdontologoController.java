package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.OdontologoRequestDTO;
import com.clinicaODontologica.UP.dto.OdontologoResponseDTO;
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
    public ResponseEntity<OdontologoResponseDTO> guardar(@RequestBody OdontologoRequestDTO dto) {

        Odontologo odontologo = new Odontologo();
        odontologo.setNombre(dto.nombre);
        odontologo.setApellido(dto.apellido);
        odontologo.setMatricula(dto.matricula);

        Odontologo guardado = odontologoService.guardar(odontologo);
        return ResponseEntity.ok(toResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoResponseDTO> buscarPorId(@PathVariable Long id) {
        Odontologo odontologo = odontologoService.buscar(id);
        return odontologo != null ?
                ResponseEntity.ok(toResponseDTO(odontologo)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<OdontologoResponseDTO>> buscarTodos() {
        List<Odontologo> lista = odontologoService.buscarTodos();
        List<OdontologoResponseDTO> respuesta = lista.stream()
                .map(this::toResponseDTO)
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OdontologoResponseDTO> actualizar(
            @PathVariable Long id, @RequestBody OdontologoRequestDTO dto) {

        Odontologo odontologo = odontologoService.buscar(id);
        if (odontologo == null) {
            return ResponseEntity.notFound().build();
        }

        odontologo.setNombre(dto.nombre);
        odontologo.setApellido(dto.apellido);
        odontologo.setMatricula(dto.matricula);

        Odontologo actualizado = odontologoService.actualizar(odontologo);
        return ResponseEntity.ok(toResponseDTO(actualizado));
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

    private OdontologoResponseDTO toResponseDTO(Odontologo odontologo) {
        OdontologoResponseDTO dto = new OdontologoResponseDTO();
        dto.id = odontologo.getId();
        dto.nombre = odontologo.getNombre();
        dto.apellido = odontologo.getApellido();
        dto.matricula = odontologo.getMatricula();
        return dto;
    }
}

package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.DomicilioRequestDTO;
import com.clinicaODontologica.UP.dto.DomicilioResponseDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.service.DomicilioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domicilio")
public class DomicilioController {

    private final DomicilioService domicilioService;

    public DomicilioController(DomicilioService domicilioService) {
        this.domicilioService = domicilioService;
    }

    @PostMapping
    public ResponseEntity<DomicilioResponseDTO> guardar(@RequestBody DomicilioRequestDTO dto) {

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(dto.calle);
        domicilio.setNumero(dto.numero);
        domicilio.setLocalidad(dto.localidad);
        domicilio.setProvincia(dto.provincia);

        Domicilio guardado = domicilioService.guardar(domicilio);
        return ResponseEntity.ok(toResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomicilioResponseDTO> buscarPorId(@PathVariable Long id) {
        Domicilio domicilio = domicilioService.buscar(id);
        return domicilio != null ?
                ResponseEntity.ok(toResponseDTO(domicilio)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DomicilioResponseDTO>> buscarTodos() {
        List<Domicilio> lista = domicilioService.buscarTodos();
        List<DomicilioResponseDTO> respuesta = lista.stream()
                .map(this::toResponseDTO)
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomicilioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody DomicilioRequestDTO dto) {

        Domicilio domicilio = domicilioService.buscar(id);
        if (domicilio == null) {
            return ResponseEntity.notFound().build();
        }

        domicilio.setCalle(dto.calle);
        domicilio.setNumero(dto.numero);
        domicilio.setLocalidad(dto.localidad);
        domicilio.setProvincia(dto.provincia);

        Domicilio actualizado = domicilioService.actualizar(domicilio);
        return ResponseEntity.ok(toResponseDTO(actualizado));
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

    private DomicilioResponseDTO toResponseDTO(Domicilio domicilio) {
        DomicilioResponseDTO dto = new DomicilioResponseDTO();
        dto.id = domicilio.getId();
        dto.calle = domicilio.getCalle();
        dto.numero = domicilio.getNumero();
        dto.localidad = domicilio.getLocalidad();
        dto.provincia = domicilio.getProvincia();
        return dto;
    }
}

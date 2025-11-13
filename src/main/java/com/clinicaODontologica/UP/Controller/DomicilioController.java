package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.DomicilioRequestDTO;
import com.clinicaODontologica.UP.dto.DomicilioResponseDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.service.DomicilioService;
import com.clinicaODontologica.UP.mapper.DomicilioMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinicaODontologica.UP.mapper.DomicilioMapper.toDTO;

@RestController
@RequestMapping("/api/domicilio")
public class DomicilioController {

    private final DomicilioService domicilioService;
    private final DomicilioMapper domicilioMapper;

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
        return ResponseEntity.ok(domicilioToDomicilioResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomicilioResponseDTO> buscarPorId(@PathVariable Long id) {
        Domicilio domicilio = domicilioService.buscar(id);
        return domicilio != null ?
                ResponseEntity.ok(domicilioToDomicilioResponseDTO(domicilio)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DomicilioResponseDTO>> buscarTodos() {
        List<DomicilioResponseDTO> respuesta = domicilioService.buscarTodos()
                .stream()
                .map(DomicilioMapper::toDTO)
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
        return ResponseEntity.ok(toDTO(actualizado));
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

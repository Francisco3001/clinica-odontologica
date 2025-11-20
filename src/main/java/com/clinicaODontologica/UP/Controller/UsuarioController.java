package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.UsuarioRequestDTO;
import com.clinicaODontologica.UP.dto.UsuarioResponseDTO;
import com.clinicaODontologica.UP.entity.Usuario;
import com.clinicaODontologica.UP.entity.UsuarioRole;
import com.clinicaODontologica.UP.service.UsuarioService;
import com.clinicaODontologica.UP.mapper.UsuarioMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = UsuarioMapper.toEntityFromRequest(dto);
        Usuario guardado = usuarioService.registrar(usuario);
        return ResponseEntity.ok(UsuarioMapper.toResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);
        return usuario != null ?
                ResponseEntity.ok(UsuarioMapper.toResponseDTO(usuario)) :
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> lista = usuarioService.buscarTodos()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto
    ) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(dto.nombre);
        usuario.setUsername(dto.username);
        usuario.setEmail(dto.email);
        usuario.setUsuarioRole(UsuarioRole.valueOf(dto.usuarioRole));

        // Solo actualizar password si viene en el JSON
        if (dto.password != null && !dto.password.isBlank()) {
            usuario.setPassword(dto.password);
        }

        Usuario actualizado = usuarioService.actualizar(usuario);
        return ResponseEntity.ok(UsuarioMapper.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);
        if (usuario == null) return ResponseEntity.notFound().build();

        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

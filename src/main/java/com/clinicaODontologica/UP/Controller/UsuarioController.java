package com.clinicaODontologica.UP.Controller;

import com.clinicaODontologica.UP.dto.UsuarioRequestDTO;
import com.clinicaODontologica.UP.dto.UsuarioResponseDTO;
import com.clinicaODontologica.UP.entity.Usuario;
import com.clinicaODontologica.UP.entity.UsuarioRole;
import com.clinicaODontologica.UP.service.UsuarioService;
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

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre);
        usuario.setUsername(dto.username);
        usuario.setPassword(dto.password);
        usuario.setEmail(dto.email);
        usuario.setUsuarioRole(UsuarioRole.valueOf(dto.usuarioRole));

        Usuario guardado = usuarioService.registrar(usuario);
        return ResponseEntity.ok(toResponseDTO(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);
        return usuario != null ? ResponseEntity.ok(toResponseDTO(usuario)) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> lista = usuarioService.buscarTodos()
                .stream()
                .map(this::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto
    ) {
        Usuario usuario = usuarioService.buscar(id);
        if (usuario == null) return ResponseEntity.notFound().build();

        usuario.setNombre(dto.nombre);
        usuario.setUsername(dto.username);
        usuario.setPassword(dto.password);
        usuario.setEmail(dto.email);
        usuario.setUsuarioRole(UsuarioRole.valueOf(dto.usuarioRole));

        Usuario actualizado = usuarioService.actualizar(usuario);

        return ResponseEntity.ok(toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);
        if (usuario == null) return ResponseEntity.notFound().build();

        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.id = usuario.getId();
        dto.nombre = usuario.getNombre();
        dto.username = usuario.getUsername();
        dto.email = usuario.getEmail();
        dto.usuarioRole = usuario.getUsuarioRole().name();
        return dto;
    }
}

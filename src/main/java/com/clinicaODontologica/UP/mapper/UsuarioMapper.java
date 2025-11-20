package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.UsuarioRequestDTO;
import com.clinicaODontologica.UP.dto.UsuarioResponseDTO;
import com.clinicaODontologica.UP.entity.Usuario;
import com.clinicaODontologica.UP.entity.UsuarioRole;

public class UsuarioMapper {

    // Entity -> ResponseDTO
    public static UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.id = entity.getId();
        dto.nombre = entity.getNombre();
        dto.username = entity.getUsername();
        dto.email = entity.getEmail();
        dto.usuarioRole = entity.getUsuarioRole() != null ? entity.getUsuarioRole().name() : null;
        return dto;
    }

    // Entity -> RequestDTO
    public static UsuarioRequestDTO toRequestDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.nombre = entity.getNombre();
        dto.username = entity.getUsername();
        dto.email = entity.getEmail();
        dto.usuarioRole = entity.getUsuarioRole() != null ? entity.getUsuarioRole().name() : null;
        // Nota: password no se incluye por seguridad
        return dto;
    }

    // ResponseDTO -> Entity
    public static Usuario toEntityFromResponse(UsuarioResponseDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(dto.id);
        usuario.setNombre(dto.nombre);
        usuario.setUsername(dto.username);
        usuario.setEmail(dto.email);
        usuario.setUsuarioRole(dto.usuarioRole != null ? UsuarioRole.valueOf(dto.usuarioRole) : null);
        return usuario;
    }

    // RequestDTO -> Entity
    public static Usuario toEntityFromRequest(UsuarioRequestDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre);
        usuario.setUsername(dto.username);
        usuario.setPassword(dto.password);
        usuario.setEmail(dto.email);
        usuario.setUsuarioRole(dto.usuarioRole != null ? UsuarioRole.valueOf(dto.usuarioRole) : null);
        return usuario;
    }


}
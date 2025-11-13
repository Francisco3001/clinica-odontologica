package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.UsuarioResponseDTO;
import com.clinicaODontologica.UP.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioResponseDTO toDTO(Usuario u) {
        if (u == null) return null;

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.id = u.getId();
        dto.nombre = u.getNombre();
        dto.username = u.getUsername();
        dto.email = u.getEmail();
        dto.usuarioRole = u.getUsuarioRole().name();
        return dto;
    }
}
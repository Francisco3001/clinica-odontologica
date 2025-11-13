package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.PacienteResponseDTO;
import com.clinicaODontologica.UP.entity.Paciente;

public class PacienteMapper {

    public static PacienteResponseDTO toDTO(Paciente p) {
        if (p == null) return null;

        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.id = p.getId();
        dto.nombre = p.getNombre();
        dto.apellido = p.getApellido();
        dto.email = p.getEmail();
        dto.fechaIngreso = p.getFechaIngreso().toString();
        dto.numeroContacto = p.getNumeroContacto();

        if (p.getDomicilio() != null) {
            dto.domicilioId = p.getDomicilio().getId();
        }

        return dto;
    }
}
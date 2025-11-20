package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.PacienteRequestDTO;
import com.clinicaODontologica.UP.dto.PacienteResponseDTO;
import com.clinicaODontologica.UP.entity.Paciente;
import java.time.LocalDate;

public class PacienteMapper {

    // Entity -> ResponseDTO
    public static PacienteResponseDTO toResponseDTO(Paciente entity) {
        if (entity == null) return null;
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.id = entity.getId();
        dto.nombre = entity.getNombre();
        dto.apellido = entity.getApellido();
        dto.email = entity.getEmail();
        dto.fechaIngreso = entity.getFechaIngreso() != null ? entity.getFechaIngreso().toString() : null;
        dto.numeroContacto = entity.getNumeroContacto();
        if (entity.getDomicilio() != null) {
            dto.domicilioId = entity.getDomicilio().getId();
        }
        return dto;
    }

    // Entity -> RequestDTO
    public static PacienteRequestDTO toRequestDTO(Paciente entity) {
        if (entity == null) return null;
        PacienteRequestDTO dto = new PacienteRequestDTO();
        dto.nombre = entity.getNombre();
        dto.apellido = entity.getApellido();
        dto.email = entity.getEmail();
        dto.fechaIngreso = entity.getFechaIngreso() != null ? entity.getFechaIngreso().toString() : null;
        dto.numeroContacto = entity.getNumeroContacto();
        if (entity.getDomicilio() != null) {
            dto.domicilioId = entity.getDomicilio().getId();
        }
        return dto;
    }

    // ResponseDTO -> Entity (sin domicilio cargado)
    public static Paciente toEntityFromResponse(PacienteResponseDTO dto) {
        if (dto == null) return null;
        Paciente paciente = new Paciente();
        paciente.setId(dto.id);
        paciente.setNombre(dto.nombre);
        paciente.setApellido(dto.apellido);
        paciente.setEmail(dto.email);
        paciente.setFechaIngreso(dto.fechaIngreso != null ? LocalDate.parse(dto.fechaIngreso) : null);
        paciente.setNumeroContacto(dto.numeroContacto);
        // Nota: domicilio debe ser cargado por separado usando domicilioId
        return paciente;
    }

    // RequestDTO -> Entity (sin domicilio cargado)
    public static Paciente toEntityFromRequest(PacienteRequestDTO dto) {
        if (dto == null) return null;
        Paciente paciente = new Paciente();
        paciente.setNombre(dto.nombre);
        paciente.setApellido(dto.apellido);
        paciente.setEmail(dto.email);
        paciente.setFechaIngreso(dto.fechaIngreso != null ? LocalDate.parse(dto.fechaIngreso) : null);
        paciente.setNumeroContacto(dto.numeroContacto);
        // Nota: domicilio debe ser cargado por separado usando domicilioId
        return paciente;
    }

}
package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.TurnoRequestDTO;
import com.clinicaODontologica.UP.dto.TurnoResponseDTO;
import com.clinicaODontologica.UP.entity.Turno;
import java.time.LocalDate;

public class TurnoMapper {

    // Entity -> ResponseDTO
    public static TurnoResponseDTO toResponseDTO(Turno entity) {
        if (entity == null) return null;
        TurnoResponseDTO dto = new TurnoResponseDTO();
        dto.id = entity.getId();
        dto.fecha = entity.getFecha() != null ? entity.getFecha().toString() : null;
        dto.pacienteId = entity.getPaciente() != null ? entity.getPaciente().getId() : null;
        dto.odontologoId = entity.getOdontologo() != null ? entity.getOdontologo().getId() : null;
        return dto;
    }

    // Entity -> RequestDTO
    public static TurnoRequestDTO toRequestDTO(Turno entity) {
        if (entity == null) return null;
        TurnoRequestDTO dto = new TurnoRequestDTO();
        dto.fecha = entity.getFecha() != null ? entity.getFecha().toString() : null;
        dto.pacienteId = entity.getPaciente() != null ? entity.getPaciente().getId() : null;
        dto.odontologoId = entity.getOdontologo() != null ? entity.getOdontologo().getId() : null;
        return dto;
    }

    // ResponseDTO -> Entity (sin paciente y odontólogo cargados)
    public static Turno toEntityFromResponse(TurnoResponseDTO dto) {
        if (dto == null) return null;
        Turno turno = new Turno();
        turno.setId(dto.id);
        turno.setFecha(dto.fecha != null ? LocalDate.parse(dto.fecha) : null);
        // Nota: paciente y odontólogo deben ser cargados por separado usando los IDs
        return turno;
    }

    // RequestDTO -> Entity (sin paciente y odontólogo cargados)
    public static Turno toEntityFromRequest(TurnoRequestDTO dto) {
        if (dto == null) return null;
        Turno turno = new Turno();
        turno.setFecha(dto.fecha != null ? LocalDate.parse(dto.fecha) : null);
        // Nota: paciente y odontólogo deben ser cargados por separado usando los IDs
        return turno;
    }


}

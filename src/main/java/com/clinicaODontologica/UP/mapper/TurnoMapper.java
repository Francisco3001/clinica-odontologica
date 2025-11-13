package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.DomicilioResponseDTO;
import com.clinicaODontologica.UP.dto.TurnoResponseDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Turno;


public class TurnoMapper {
    public static TurnoResponseDTO toDTO(Turno t) {
        if (t == null) return null;

        TurnoResponseDTO dto = new TurnoResponseDTO();
        dto.id = t.getId();
        dto.fecha = t.getFecha().toString();
        dto.pacienteId = t.getPaciente().getId();
        dto.odontologoId = t.getOdontologo().getId();
        return dto;
    }
}

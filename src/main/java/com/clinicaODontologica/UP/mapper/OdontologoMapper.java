package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.OdontologoResponseDTO;
import com.clinicaODontologica.UP.entity.Odontologo;

public class OdontologoMapper {

    public static OdontologoResponseDTO toDTO(Odontologo o) {
        if (o == null) return null;

        OdontologoResponseDTO dto = new OdontologoResponseDTO();
        dto.id = o.getId();
        dto.nombre = o.getNombre();
        dto.apellido = o.getApellido();
        dto.matricula = o.getMatricula();
        return dto;
    }
}
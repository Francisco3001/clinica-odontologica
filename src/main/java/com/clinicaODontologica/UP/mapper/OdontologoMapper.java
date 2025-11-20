package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.OdontologoRequestDTO;
import com.clinicaODontologica.UP.dto.OdontologoResponseDTO;
import com.clinicaODontologica.UP.entity.Odontologo;

public class OdontologoMapper {

    // Entity -> ResponseDTO
    public static OdontologoResponseDTO toResponseDTO(Odontologo entity) {
        if (entity == null) return null;
        OdontologoResponseDTO dto = new OdontologoResponseDTO();
        dto.id = entity.getId();
        dto.nombre = entity.getNombre();
        dto.apellido = entity.getApellido();
        dto.matricula = entity.getMatricula();
        return dto;
    }

    // Entity -> RequestDTO
    public static OdontologoRequestDTO toRequestDTO(Odontologo entity) {
        if (entity == null) return null;
        OdontologoRequestDTO dto = new OdontologoRequestDTO();
        dto.nombre = entity.getNombre();
        dto.apellido = entity.getApellido();
        dto.matricula = entity.getMatricula();
        return dto;
    }

    // ResponseDTO -> Entity
    public static Odontologo toEntityFromResponse(OdontologoResponseDTO dto) {
        if (dto == null) return null;
        Odontologo odontologo = new Odontologo();
        odontologo.setId(dto.id);
        odontologo.setNombre(dto.nombre);
        odontologo.setApellido(dto.apellido);
        odontologo.setMatricula(dto.matricula);
        return odontologo;
    }

    // RequestDTO -> Entity
    public static Odontologo toEntityFromRequest(OdontologoRequestDTO dto) {
        if (dto == null) return null;
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre(dto.nombre);
        odontologo.setApellido(dto.apellido);
        odontologo.setMatricula(dto.matricula);
        return odontologo;
    }


}
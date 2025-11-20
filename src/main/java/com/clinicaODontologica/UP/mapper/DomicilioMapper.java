package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.*;
import com.clinicaODontologica.UP.entity.Domicilio;

public class DomicilioMapper {

    // Entity -> ResponseDTO
    public static DomicilioResponseDTO toResponseDTO(Domicilio entity) {
        if (entity == null) return null;
        DomicilioResponseDTO dto = new DomicilioResponseDTO();
        dto.id = entity.getId();
        dto.calle = entity.getCalle();
        dto.numero = entity.getNumero();
        dto.localidad = entity.getLocalidad();
        dto.provincia = entity.getProvincia();
        return dto;
    }

    // Entity -> RequestDTO
    public static DomicilioRequestDTO toRequestDTO(Domicilio entity) {
        if (entity == null) return null;
        DomicilioRequestDTO dto = new DomicilioRequestDTO();
        dto.calle = entity.getCalle();
        dto.numero = entity.getNumero();
        dto.localidad = entity.getLocalidad();
        dto.provincia = entity.getProvincia();
        return dto;
    }

    // ResponseDTO -> Entity
    public static Domicilio toEntityFromResponse(DomicilioResponseDTO dto) {
        if (dto == null) return null;
        Domicilio domicilio = new Domicilio();
        domicilio.setId(dto.id);
        domicilio.setCalle(dto.calle);
        domicilio.setNumero(dto.numero);
        domicilio.setLocalidad(dto.localidad);
        domicilio.setProvincia(dto.provincia);
        return domicilio;
    }

    // RequestDTO -> Entity
    public static Domicilio toEntityFromRequest(DomicilioRequestDTO dto) {
        if (dto == null) return null;
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(dto.calle);
        domicilio.setNumero(dto.numero);
        domicilio.setLocalidad(dto.localidad);
        domicilio.setProvincia(dto.provincia);
        return domicilio;
    }

}
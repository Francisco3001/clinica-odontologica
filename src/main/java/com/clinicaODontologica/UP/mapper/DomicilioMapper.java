package com.clinicaODontologica.UP.mapper;

import com.clinicaODontologica.UP.dto.*;
import com.clinicaODontologica.UP.entity.Domicilio;

public class DomicilioMapper {

    public static DomicilioResponseDTO domicilioToDomicilioResponseDTO(Domicilio d) {
        if (d == null) return null;
        DomicilioResponseDTO dto = new DomicilioResponseDTO();
        dto.id = d.getId();
        dto.calle = d.getCalle();
        dto.numero = d.getNumero();
        dto.localidad = d.getLocalidad();
        dto.provincia = d.getProvincia();
        return dto;
    }

    public static Domicilio domicilioRequestDTOToDomicilio(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(dto.calle);
        domicilio.setNumero(dto.numero);
        domicilio.setLocalidad(dto.localidad);
        domicilio.setProvincia(dto.provincia);
        return domicilio;
    }

}
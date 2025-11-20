package com.clinicaODontologica.UP.dto;

import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.entity.Paciente;

import java.time.LocalDate;

public class TurnoRequestDTO {
    public Long pacienteId;
    public Long odontologoId;
    public String fecha;  // formato "2025-11-30"
}

package com.clinicaODontologica.UP;

import com.clinicaODontologica.UP.dto.TurnoRequestDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import com.clinicaODontologica.UP.service.DomicilioService;
import com.clinicaODontologica.UP.service.OdontologoService;
import com.clinicaODontologica.UP.service.PacienteService;
import com.clinicaODontologica.UP.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnosTestsIntegracion {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private DomicilioService domicilioService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PacienteService pacienteService;

    private void cargarDatos(){
        Paciente paciente = pacienteService.guardar(new Paciente("Juan", "Carlos", 111111111L, LocalDate.of(2025,11,11), new Domicilio("casa", 23, "asd", "asd"), "juan"));
        Odontologo odontologo = odontologoService.guardar(new Odontologo("fran", "noceda", "asdasdasd"));
        Turno turno = turnoService.guardar(new Turno(paciente, odontologo, LocalDate.of(2025,11,11)));
    }




}

package com.clinicaODontologica.UP.Integracion;

import com.clinicaODontologica.UP.dto.TurnoRequestDTO;
import com.clinicaODontologica.UP.dto.TurnoResponseDTO;
import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import com.clinicaODontologica.UP.repository.TurnoRepository;
import com.clinicaODontologica.UP.service.OdontologoService;
import com.clinicaODontologica.UP.service.PacienteService;
import com.clinicaODontologica.UP.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnosTestIntegracion {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    OdontologoRepository odontologoRepository;

    @Autowired
    DomicilioRepository domicilioRepository;

    @Autowired
    TurnoRepository turnoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;
    private Paciente pacienteGuardado;
    private Odontologo odontologoGuardado;
    private TurnoResponseDTO turnoCreado;


    @BeforeEach
    public void cargaDatos() {
        turnoRepository.deleteAll();
        pacienteRepository.deleteAll();
        odontologoRepository.deleteAll();
        domicilioRepository.deleteAll();

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Crear y guardar domicilio
        Domicilio domicilio = new Domicilio("julio", 777, "perez", "ex");

        // Crear y guardar paciente
        Paciente paciente = new Paciente("julio", "julio", 1711111L,
                LocalDate.of(2023, 10, 1), domicilio, "julio@gmail.com");
        pacienteGuardado = pacienteService.guardar(paciente);

        // Crear y guardar odontólogo
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("julio");
        odontologo.setApellido("julio");
        odontologo.setMatricula("33");
        odontologoGuardado = odontologoService.guardar(odontologo);
    }

    @Test
    public void registrarTurno() throws Exception {
        TurnoRequestDTO turnoAGuardar = new TurnoRequestDTO();
        turnoAGuardar.fecha = "2025-12-12";
        turnoAGuardar.pacienteId = pacienteGuardado.getId();
        turnoAGuardar.odontologoId = odontologoGuardado.getId();

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.post("/api/turno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(turnoAGuardar))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String json = resultado.getResponse().getContentAsString();
        TurnoResponseDTO creado = mapper.readValue(json, TurnoResponseDTO.class);
        assertNotNull(creado.id);
        assertEquals(turnoAGuardar.fecha, creado.fecha);
        assertEquals(pacienteGuardado.getId(), creado.pacienteId);
        assertEquals(odontologoGuardado.getId(), creado.odontologoId);

        this.turnoCreado = creado;
    }

    @Test
    public void listarTurnos() throws Exception {
        registrarTurno();

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/api/turno")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String body = respuesta.getResponse().getContentAsString();
        assertFalse(body.isEmpty());
        assertTrue(body.contains(String.valueOf(turnoCreado.id)));
    }

    @Test
    public void obtenerTurnoPorId() throws Exception {
        registrarTurno();

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/api/turno/" + turnoCreado.id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String json = respuesta.getResponse().getContentAsString();
        TurnoResponseDTO encontrado = mapper.readValue(json, TurnoResponseDTO.class);
        assertEquals(turnoCreado.id, encontrado.id);
        assertEquals(turnoCreado.fecha, encontrado.fecha);
    }

    @Test
    public void actualizarTurno() throws Exception {
        registrarTurno();

        TurnoRequestDTO turnoActualizar = new TurnoRequestDTO();
        turnoActualizar.fecha = "2025-12-31";
        turnoActualizar.pacienteId = pacienteGuardado.getId();
        turnoActualizar.odontologoId = odontologoGuardado.getId();

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.put("/api/turno/" + turnoCreado.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(turnoActualizar))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String json = resultado.getResponse().getContentAsString();
        TurnoResponseDTO actualizado = mapper.readValue(json, TurnoResponseDTO.class);
        assertEquals("2025-12-31", actualizado.fecha);
    }

    @Test
    public void eliminarTurno() throws Exception {
        registrarTurno();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/turno/" + turnoCreado.id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // al pedir por id debe devolver NOT_FOUND
        mockMvc.perform(MockMvcRequestBuilders.get("/api/turno/" + turnoCreado.id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
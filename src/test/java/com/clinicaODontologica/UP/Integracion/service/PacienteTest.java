package com.clinicaODontologica.UP.Integracion.service;

import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.Exception.ResourceNotFoundException;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import com.clinicaODontologica.UP.repository.TurnoRepository;
import com.clinicaODontologica.UP.service.PacienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PacienteTest {
    @Autowired
    PacienteService pacienteService;

    @Autowired
    DomicilioRepository domicilioRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    TurnoRepository turnoRepository;

    Domicilio domicilioInicial;
    Paciente pacienteInicial;

    @BeforeEach
    public void setup(){
        turnoRepository.deleteAll();
        pacienteRepository.deleteAll();
        domicilioRepository.deleteAll();
        domicilioInicial = new Domicilio("Evergreen", 742, "Springfield", "AnyState");
        pacienteInicial = new Paciente("Homero", "Simpson", 123456L, LocalDate.now(), domicilioInicial, "homero@gmail.com");
        pacienteInicial = pacienteService.guardar(pacienteInicial);
    }

    @Test
    public void buscarPaciente() {
        //CUANDO
        Paciente paciente = pacienteService.buscar(pacienteInicial.getId());
        System.out.println("datos encontrados: " + paciente.getNombre() + " " + paciente.getApellido());
        //ENTONCES
        Assertions.assertNotNull(paciente);
        Assertions.assertEquals("Homero", paciente.getNombre());
    }

    @Test
    public void guardarPaciente() {
        //DADO
        Domicilio domicilioBart = new Domicilio("Evergreen", 743, "Springfield", "AnyState");
        Paciente pacienteAGuardar = new Paciente("Bart", "Simpson", 123457L, LocalDate.of(2025, 10, 10), domicilioBart, "bart@gmail.com");

        //CUANDO
        Paciente guardado = pacienteService.guardar(pacienteAGuardar);

        Paciente pacienteBuscado = pacienteService.buscar(guardado.getId());

        //ENTONCES
        Assertions.assertNotNull(guardado);
        Assertions.assertNotNull(pacienteBuscado);
        Assertions.assertEquals(
                pacienteAGuardar.getNombre(),
                pacienteBuscado.getNombre()
        );

        Assertions.assertEquals(
                pacienteAGuardar.getApellido(),
                pacienteBuscado.getApellido()
        );
    }

    @Test
    public void eliminarPaciente() {
        //DADO
        Domicilio domicilioTemp = new Domicilio("Main", 1, "Springfield", "AnyState");
        Paciente nuevo = new Paciente("Temporal", "Borrar", 111111L, LocalDate.now(), domicilioTemp, "temp@example.com");
        nuevo = pacienteService.guardar(nuevo);

        //CUANDO
        pacienteService.eliminar(nuevo.getId());

        //ENTONCES
        Paciente buscado = pacienteService.buscar(nuevo.getId());
        Assertions.assertNull(buscado);
    }

    @Test
    public void actualizarPaciente() {
        //DADO
        domicilioInicial = pacienteInicial.getDomicilio();
        Paciente pacienteAActualizar = new Paciente(
                pacienteInicial.getId(),
                "Abraham",
                "Simpson",
                123456L,
                LocalDate.of(2025, 10, 10),
                domicilioInicial,
                "homero@gmail.com"
        );

        Assertions.assertEquals("Homero", pacienteService.buscar(pacienteInicial.getId()).getNombre());

        //CUANDO
        pacienteService.actualizar(pacienteAActualizar);

        Paciente pacienteBuscado = pacienteService.buscar(pacienteInicial.getId());

        //ENTONCES
        Assertions.assertEquals("Abraham", pacienteBuscado.getNombre());
    }

    @Test
    public void buscarTodosLosPacientes(){
        //DADO
        List<Paciente> iniciales = pacienteService.buscarTodos();
        int tamanioInicial = iniciales.size();

        Domicilio domicilioApu = new Domicilio("Evergreen", 744, "Springfield", "AnyState");
        Paciente pacienteAGuardar = new Paciente("Apu", "Nahasapeemapetilon", 123458L, LocalDate.of(2025, 10, 10), domicilioApu, "apu@example.com");
        pacienteService.guardar(pacienteAGuardar);

        //CUANDO
        List<Paciente> pacientes = pacienteService.buscarTodos();
        pacientes.forEach(paciente ->
            System.out.println(paciente.getNombre() + " " + paciente.getApellido())
        );

        //ENTONCES
        Assertions.assertEquals(tamanioInicial + 1, pacientes.size());
    }
}

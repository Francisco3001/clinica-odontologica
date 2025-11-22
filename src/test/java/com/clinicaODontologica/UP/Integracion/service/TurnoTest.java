package com.clinicaODontologica.UP.Integracion.service;

import com.clinicaODontologica.UP.entity.Domicilio;
import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.entity.Paciente;
import com.clinicaODontologica.UP.entity.Turno;
import com.clinicaODontologica.UP.Exception.ResourceNotFoundException;
import com.clinicaODontologica.UP.repository.DomicilioRepository;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import com.clinicaODontologica.UP.repository.PacienteRepository;
import com.clinicaODontologica.UP.repository.TurnoRepository;
import com.clinicaODontologica.UP.service.TurnoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class TurnoTest {
    @Autowired
    TurnoService turnoService;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    OdontologoRepository odontologoRepository;

    @Autowired
    DomicilioRepository domicilioRepository;

    @Autowired
    TurnoRepository turnoRepository;

    Paciente pacienteInicial;
    Odontologo odontologoInicial;
    Turno turnoInicial;

    @BeforeEach
    public void setup(){
        turnoRepository.deleteAll();
        pacienteRepository.deleteAll();
        odontologoRepository.deleteAll();
        domicilioRepository.deleteAll();

        Domicilio domicilio = new Domicilio("Evergreen", 742, "Springfield", "AnyState");
        Paciente paciente = new Paciente("Homero", "Simpson", 123456L, LocalDate.now(), domicilio, "homero@gmail.com");
        pacienteInicial = pacienteRepository.save(paciente);

        odontologoInicial = new Odontologo();
        odontologoInicial.setNombre("Dr.");
        odontologoInicial.setApellido("Nick");
        odontologoInicial.setMatricula("MAT123");
        odontologoInicial = odontologoRepository.save(odontologoInicial);

        turnoInicial = new Turno();
        turnoInicial.setPaciente(pacienteInicial);
        turnoInicial.setOdontologo(odontologoInicial);
        turnoInicial.setFecha(LocalDate.of(2025, 11, 15));
        turnoInicial = turnoRepository.save(turnoInicial);
    }

    @Test
    public void buscarTurnoPorId() {
        // CUANDO
        Turno turno = turnoService.buscar(turnoInicial.getId());
        System.out.println("datos encontrados: Turno ID " + turno.getId() + " - Fecha: " + turno.getFecha());
        // ENTONCES
        Assertions.assertNotNull(turno);
        Assertions.assertEquals(turnoInicial.getId(), turno.getId());
    }

    @Test
    public void guardarTurno() {
        // DADO
        Domicilio domicilioNuevo = new Domicilio("Main", 1, "Springfield", "AnyState");
        Paciente pacienteNuevo = new Paciente("Bart", "Simpson", 123457L, LocalDate.now(), domicilioNuevo, "bart@gmail.com");
        pacienteNuevo = pacienteRepository.save(pacienteNuevo);

        Odontologo odontologoNuevo = new Odontologo();
        odontologoNuevo.setNombre("Dr.");
        odontologoNuevo.setApellido("Zoidberg");
        odontologoNuevo.setMatricula("MAT456");
        odontologoNuevo = odontologoRepository.save(odontologoNuevo);

        Turno turnoNuevo = new Turno();
        turnoNuevo.setFecha(LocalDate.of(2025, 11, 16));
        turnoNuevo.setPaciente(pacienteNuevo);
        turnoNuevo.setOdontologo(odontologoNuevo);

        // CUANDO
        Turno guardado = turnoService.guardar(turnoNuevo);
        Turno turnoBuscado = turnoService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNotNull(guardado);
        Assertions.assertNotNull(turnoBuscado);
        Assertions.assertEquals(turnoNuevo.getFecha(), turnoBuscado.getFecha());
        Assertions.assertEquals(pacienteNuevo.getId(), turnoBuscado.getPaciente().getId());
        Assertions.assertEquals(odontologoNuevo.getId(), turnoBuscado.getOdontologo().getId());
    }

    @Test
    public void eliminarTurno() {
        // DADO
        Domicilio domicilioTemp = new Domicilio("Temp", 2, "Springfield", "AnyState");
        Paciente pacienteTemp = new Paciente("Temporal", "Borrar", 111111L, LocalDate.now(), domicilioTemp, "temp@example.com");
        pacienteTemp = pacienteRepository.save(pacienteTemp);

        Odontologo odontologoTemp = new Odontologo();
        odontologoTemp.setNombre("Dr.");
        odontologoTemp.setApellido("Temp");
        odontologoTemp.setMatricula("MAT789");
        odontologoTemp = odontologoRepository.save(odontologoTemp);

        Turno turnoTemp = new Turno();
        turnoTemp.setFecha(LocalDate.of(2025, 11, 17));
        turnoTemp.setPaciente(pacienteTemp);
        turnoTemp.setOdontologo(odontologoTemp);
        Turno turnoGuardado = turnoService.guardar(turnoTemp);

        // CUANDO
        turnoService.eliminar(turnoGuardado.getId());

        // ENTONCES
        Turno buscado = turnoService.buscar(turnoGuardado.getId());
        Assertions.assertNull(buscado);
    }

    @Test
    public void actualizarTurno() {
        // DADO
        Turno turnoAActualizar = new Turno();
        turnoAActualizar.setId(turnoInicial.getId());
        turnoAActualizar.setFecha(LocalDate.of(2025, 11, 18));
        turnoAActualizar.setPaciente(pacienteInicial);
        turnoAActualizar.setOdontologo(odontologoInicial);

        Assertions.assertEquals(LocalDate.of(2025, 11, 15), turnoService.buscar(turnoInicial.getId()).getFecha());

        // CUANDO
        turnoService.actualizar(turnoAActualizar);
        Turno turnoBuscado = turnoService.buscar(turnoInicial.getId());

        // ENTONCES
        Assertions.assertEquals(LocalDate.of(2025, 11, 18), turnoBuscado.getFecha());
    }

    @Test
    public void listarTurnos() {
        // DADO
        List<Turno> iniciales = turnoService.buscarTodos();
        int tamanioInicial = iniciales.size();

        Domicilio domicilioNuevo = new Domicilio("New", 3, "Springfield", "AnyState");
        Paciente pacienteNuevo = new Paciente("Apu", "Nahasapeemapetilon", 123458L, LocalDate.now(), domicilioNuevo, "apu@example.com");
        pacienteNuevo = pacienteRepository.save(pacienteNuevo);

        Odontologo odontologoNuevo = new Odontologo();
        odontologoNuevo.setNombre("Dr.");
        odontologoNuevo.setApellido("New");
        odontologoNuevo.setMatricula("MAT000");
        odontologoNuevo = odontologoRepository.save(odontologoNuevo);

        Turno turnoNuevo = new Turno();
        turnoNuevo.setFecha(LocalDate.of(2025, 11, 19));
        turnoNuevo.setPaciente(pacienteNuevo);
        turnoNuevo.setOdontologo(odontologoNuevo);
        turnoService.guardar(turnoNuevo);

        // CUANDO
        List<Turno> turnos = turnoService.buscarTodos();
        turnos.forEach(turno ->
            System.out.println("Turno ID: " + turno.getId() + " - Fecha: " + turno.getFecha())
        );

        // ENTONCES
        Assertions.assertEquals(tamanioInicial + 1, turnos.size());
    }
}

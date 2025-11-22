package com.clinicaODontologica.UP.Integracion.service;

import com.clinicaODontologica.UP.entity.Odontologo;
import com.clinicaODontologica.UP.Exception.ResourceNotFoundException;
import com.clinicaODontologica.UP.repository.OdontologoRepository;
import com.clinicaODontologica.UP.repository.TurnoRepository;
import com.clinicaODontologica.UP.service.OdontologoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class OdontologoTest {

    @Autowired
    OdontologoService odontologoService;

    @Autowired
    OdontologoRepository odontologoRepository;

    @Autowired
    TurnoRepository turnoRepository;

    Odontologo doctorHibert;

    @BeforeEach
    public void setup(){
        turnoRepository.deleteAll();
        odontologoRepository.deleteAll();
        doctorHibert = new Odontologo();
        doctorHibert.setNombre("Doctor");
        doctorHibert.setApellido("Hibert");
        doctorHibert.setMatricula("12345");
        odontologoService.guardar(doctorHibert);
    }

    @Test
    public void buscarOdontologo() {
        //DADO
        //CUANDO
        Odontologo odontologo = odontologoService.buscar(doctorHibert.getId());
        System.out.println("datos encontrados: " + odontologo.getNombre() + " " + odontologo.getApellido());
        //ENTONCES
        Assertions.assertNotNull(odontologo);
        Assertions.assertEquals("Doctor", odontologo.getNombre());
    }

    @Test
    public void guardarOdontologo() {
        //DADO
        Odontologo odontologoAGuardar = new Odontologo();
        odontologoAGuardar.setNombre("Apu");
        odontologoAGuardar.setApellido("Nahasapeemapetilon");
        odontologoAGuardar.setMatricula("67891234678");

        //CUANDO
        Odontologo guardado = odontologoService.guardar(odontologoAGuardar);

        Odontologo odontologoBuscado = odontologoService.buscar(guardado.getId());

        //ENTONCES
        Assertions.assertNotNull(guardado);
        Assertions.assertNotNull(odontologoBuscado);
        Assertions.assertEquals(
                odontologoAGuardar.getNombre(),
                odontologoBuscado.getNombre()
        );
        Assertions.assertEquals(
                odontologoAGuardar.getApellido(),
                odontologoBuscado.getApellido()
        );
    }

    @Test
    public void eliminarOdontologo() {
        //DADO
        Odontologo nuevo = new Odontologo();
        nuevo.setNombre("Temporal");
        nuevo.setApellido("Borrar");
        nuevo.setMatricula("99999");
        odontologoService.guardar(nuevo);

        //CUANDO
        odontologoService.eliminar(nuevo.getId());

        //ENTONCES
        Odontologo buscado = odontologoService.buscar(nuevo.getId());
        Assertions.assertNull(buscado);
    }

    @Test
    public void actualizarOdontologo() {
        //DADO
        Odontologo odontologoAActualizar = new Odontologo();
        odontologoAActualizar.setId(doctorHibert.getId());
        odontologoAActualizar.setNombre("Apu");
        odontologoAActualizar.setApellido("Nahasapeemapetilon");
        odontologoAActualizar.setMatricula("54321");

        Assertions.assertEquals("Doctor", odontologoService.buscar(doctorHibert.getId()).getNombre());

        //CUANDO
        odontologoService.actualizar(odontologoAActualizar);

        Odontologo odontologoBuscado = odontologoService.buscar(doctorHibert.getId());

        //ENTONCES
        Assertions.assertEquals("Apu", odontologoBuscado.getNombre());
    }

    @Test
    public void buscarTodosLosOdontologos(){
        //DADO
        List<Odontologo> iniciales = odontologoService.buscarTodos();
        int tamanioInicial = iniciales.size();

        Odontologo odontologoAGuardar = new Odontologo();
        odontologoAGuardar.setNombre("Apu");
        odontologoAGuardar.setApellido("Nahasapeemapetilon");
        odontologoAGuardar.setMatricula("67890");
        odontologoService.guardar(odontologoAGuardar);

        //CUANDO
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        odontologos.forEach(odontologo ->
            System.out.println(odontologo.getNombre() + " " + odontologo.getApellido())
        );

        //ENTONCES
        Assertions.assertEquals(tamanioInicial + 1, odontologos.size());
    }

}
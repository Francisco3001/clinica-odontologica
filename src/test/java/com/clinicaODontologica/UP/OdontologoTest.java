package com.clinicaODontologica.UP;

import dao.BD;
import dao.OdontologoDAOH2;
import model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.util.List;

public class OdontologoTest {
    private OdontologoService prepararService() { // común en todos los tests
        BD.crearTablas();
        return new OdontologoService(new OdontologoDAOH2());
    }

    @Test
    public void agregarOdontologo() {
        // DADO
        OdontologoService odontologoService = prepararService();

        // CUANDO
        Odontologo odontologo = new Odontologo("Juan", "Pérez", "MAT-1234");
        Odontologo guardado = odontologoService.guardar(odontologo);

        // ENTONCES
        Assertions.assertNotNull(guardado.getId(), "El odontólogo debería tener un ID asignado");
        Assertions.assertEquals("Juan", guardado.getNombre());
        Assertions.assertEquals("Pérez", guardado.getApellido());
    }

    @Test
    public void buscarOdontologoPorId() {
        // DADO
        OdontologoService odontologoService = prepararService();

        // CUANDO
        Odontologo odontologo = new Odontologo("Laura", "Gómez", "MAT-4321");
        Odontologo guardado = odontologoService.guardar(odontologo);

        Odontologo encontrado = odontologoService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Laura", encontrado.getNombre());
        Assertions.assertEquals("Gómez", encontrado.getApellido());
    }

    @Test
    public void actualizarOdontologo() {
        // DADO
        OdontologoService odontologoService = prepararService();

        // CUANDO
        Odontologo odontologo = new Odontologo("Carlos", "Fernández", "MAT-7777");
        Odontologo guardado = odontologoService.guardar(odontologo);

        // Actualizamos datos
        guardado.setApellido("Ramírez");
        guardado.setMatricula("MAT-8888");
        odontologoService.actualizar(guardado);

        Odontologo actualizado = odontologoService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertEquals("Ramírez", actualizado.getApellido());
        Assertions.assertEquals("MAT-8888", actualizado.getMatricula());
    }

    @Test
    public void eliminarOdontologo() {
        // DADO
        OdontologoService odontologoService = prepararService();

        // CUANDO
        Odontologo odontologo = new Odontologo("María", "López", "MAT-5555");
        Odontologo guardado = odontologoService.guardar(odontologo);

        odontologoService.eliminar(guardado.getId());
        Odontologo eliminado = odontologoService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNull(eliminado, "El odontólogo debería haberse eliminado");
    }

    @Test
    public void buscarTodosLosOdontologos() {
        // DADO
        OdontologoService odontologoService = prepararService();

        Odontologo o1 = new Odontologo("Ana", "Torres", "MAT-1111");
        Odontologo o2 = new Odontologo("Pablo", "Suárez", "MAT-2222");

        odontologoService.guardar(o1);
        odontologoService.guardar(o2);

        // CUANDO
        List<Odontologo> lista = odontologoService.buscarTodos();

        // ENTONCES
        Assertions.assertFalse(lista.isEmpty(), "La lista no debería estar vacía");
        Assertions.assertTrue(lista.size() >= 2, "Debería haber al menos 2 odontólogos guardados");
    }

}

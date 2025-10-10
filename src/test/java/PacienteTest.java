import dao.BD;
import dao.DomicilioDAOH2;
import dao.PacienteDAOH2;
import model.Domicilio;
import model.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.DomicilioService;
import service.PacienteService;

import java.util.List;

public class PacienteTest {

    private PacienteService prepararService() { //comun en todos los tests
        BD.crearTablas();
        return new PacienteService(new PacienteDAOH2());
    }

    private DomicilioService prepararDomicilioService() {
        return new DomicilioService(new DomicilioDAOH2());
    }

    @Test
    public void agregarPaciente() {
        // DADO
        PacienteService pacienteService = prepararService();
        DomicilioService domicilioService = prepararDomicilioService();

        // CUANDO
        Domicilio domicilio = new Domicilio("luis viale", 123, "caba", "ccaba");
        domicilio = domicilioService.guardar(domicilio);

        Paciente paciente = new Paciente("francisco", "noceda", 11111, "110105", domicilio, "francisco@gmail.com");
        Paciente guardado = pacienteService.guardar(paciente);

        // ENTONCES
        Assertions.assertNotNull(guardado.getId(), "El paciente debería tener un ID asignado");
        Assertions.assertEquals("francisco", guardado.getNombre());
        Assertions.assertEquals("noceda", guardado.getApellido());
    }
    @Test
    public void buscarPacientePorId() {
        // DADO
        PacienteService pacienteService = prepararService();
        DomicilioService domicilioService = prepararDomicilioService();

        // CUANDO
        Domicilio domicilio = new Domicilio("avenida siempre viva", 742, "springfield", "buenos aires");
        domicilio = domicilioService.guardar(domicilio);

        Paciente paciente = new Paciente("Homero", "Simpson", 55555, "2025-10-10", domicilio, "homero@springfield.com");
        Paciente guardado = pacienteService.guardar(paciente);

        Paciente encontrado = pacienteService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Homero", encontrado.getNombre());
        Assertions.assertEquals("Simpson", encontrado.getApellido());
    }


    @Test
    public void eliminarPaciente() {
        // DADO
        PacienteService pacienteService = prepararService();
        DomicilioService domicilioService = prepararDomicilioService();

        // CUANDO
        Domicilio domicilio = new Domicilio("avenida siempre viva", 742, "springfield", "buenos aires");
        domicilio = domicilioService.guardar(domicilio);

        Paciente paciente = new Paciente("Homero", "Simpson", 55555, "2025-10-10", domicilio, "homero@springfield.com");
        Paciente guardado = pacienteService.guardar(paciente);

        pacienteService.eliminar(guardado.getId());
        Paciente eliminado = pacienteService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNull(eliminado, "El odontólogo debería haberse eliminado");
    }



    @Test
    public void actualizarPaciente() {
        // DADO
        PacienteService pacienteService = prepararService();
        DomicilioService domicilioService = prepararDomicilioService();

        // CUANDO
        Domicilio domicilio = new Domicilio("avenida siempre viva", 742, "springfield", "buenos aires");
        domicilio = domicilioService.guardar(domicilio);

        Paciente paciente = new Paciente("Homero", "Simpson", 55555, "2025-10-10", domicilio, "homero@springfield.com");
        Paciente guardado = pacienteService.guardar(paciente);

        //ACTUALIZO LOS DATOS DEL PACIENTE
        guardado.setEmail("bartolomeo@springfield.com");
        guardado.setApellido("Thompson");
        pacienteService.actualizar(guardado);

        Paciente actualizado = pacienteService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertEquals("Thompson", actualizado.getApellido());
        Assertions.assertEquals("bartolomeo@springfield.com", actualizado.getEmail());
    }

    @Test
    public void buscarTodosLosPacientes() {
        // DADO
        PacienteService pacienteService = prepararService();
        DomicilioService domicilioService = prepararDomicilioService();

        Domicilio d1 = new Domicilio("calle 1", 10, "quilmes", "buenos aires");
        Domicilio d2 = new Domicilio("calle 2", 20, "avellaneda", "buenos aires");

        d1 = domicilioService.guardar(d1);
        d2 = domicilioService.guardar(d2);

        Paciente paciente1 = new Paciente("Marge", "Bouvier", 11111, "2025-10-10", d1, "marge@springfield.com");
        Paciente paciente2 = new Paciente("Maggie", "Simpson", 22222, "2025-10-10", d2, "maggie@springfield.com");

        Paciente guardado1 = pacienteService.guardar(paciente1);
        Paciente guardado2 = pacienteService.guardar(paciente2);

        List<Paciente> lista = pacienteService.buscarTodos();

        Assertions.assertFalse(lista.isEmpty(), "La lista no debería estar vacía");
        Assertions.assertTrue(lista.size() >= 2, "Debería haber al menos 2 pacientes guardados");
    }
}

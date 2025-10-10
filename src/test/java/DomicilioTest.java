import dao.BD;
import dao.DomicilioDAOH2;
import model.Domicilio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.DomicilioService;

import java.util.List;

public class DomicilioTest {

    private DomicilioService prepararService() { // común en todos los tests
        BD.crearTablas();
        return new DomicilioService(new DomicilioDAOH2());
    }

    @Test
    public void agregarDomicilio() {
        // DADO
        DomicilioService domicilioService = prepararService();

        // CUANDO
        Domicilio domicilio = new Domicilio("Av. Corrientes", 1234, "CABA", "Buenos Aires");
        Domicilio guardado = domicilioService.guardar(domicilio);

        // ENTONCES
        Assertions.assertNotNull(guardado.getId(), "El domicilio debería tener un ID asignado");
        Assertions.assertEquals("Av. Corrientes", guardado.getCalle());
        Assertions.assertEquals(1234, guardado.getNumero());
    }

    @Test
    public void buscarDomicilioPorId() {
        // DADO
        DomicilioService domicilioService = prepararService();

        // CUANDO
        Domicilio domicilio = new Domicilio("San Martín", 456, "La Plata", "Buenos Aires");
        Domicilio guardado = domicilioService.guardar(domicilio);

        Domicilio encontrado = domicilioService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("San Martín", encontrado.getCalle());
        Assertions.assertEquals("La Plata", encontrado.getLocalidad());
    }

    @Test
    public void actualizarDomicilio() {
        // DADO
        DomicilioService domicilioService = prepararService();

        // CUANDO
        Domicilio domicilio = new Domicilio("Belgrano", 789, "Rosario", "Santa Fe");
        Domicilio guardado = domicilioService.guardar(domicilio);

        // Actualizamos datos
        guardado.setNumero(999);
        guardado.setProvincia("Entre Ríos");
        domicilioService.actualizar(guardado);

        Domicilio actualizado = domicilioService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertEquals(999, actualizado.getNumero());
        Assertions.assertEquals("Entre Ríos", actualizado.getProvincia());
    }

    @Test
    public void eliminarDomicilio() {
        // DADO
        DomicilioService domicilioService = prepararService();

        // CUANDO
        Domicilio domicilio = new Domicilio("Mitre", 100, "Lanús", "Buenos Aires");
        Domicilio guardado = domicilioService.guardar(domicilio);

        domicilioService.eliminar(guardado.getId());
        Domicilio eliminado = domicilioService.buscar(guardado.getId());

        // ENTONCES
        Assertions.assertNull(eliminado, "El domicilio debería haberse eliminado");
    }

    @Test
    public void buscarTodosLosDomicilios() {
        // DADO
        DomicilioService domicilioService = prepararService();

        domicilioService.guardar(new Domicilio("Calle 1", 10, "Quilmes", "Buenos Aires"));
        domicilioService.guardar(new Domicilio("Calle 2", 20, "Avellaneda", "Buenos Aires"));

        // CUANDO
        List<Domicilio> lista = domicilioService.buscarTodos();

        // ENTONCES
        Assertions.assertFalse(lista.isEmpty(), "La lista no debería estar vacía");
        Assertions.assertTrue(lista.size() >= 2, "Debería haber al menos 2 domicilios guardados");
    }
}

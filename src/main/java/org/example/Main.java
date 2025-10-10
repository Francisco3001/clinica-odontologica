import dao.BD;
import dao.PacienteDAOH2;
import model.Domicilio;
import model.Paciente;

import java.sql.Connection;
import java.sql.Statement;

import static dao.BD.*;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = BD.getConnection();
             Statement stmt = conn.createStatement()) {

            // Crear tablas
            stmt.execute(SQL_DROP_CREATE_DOMICILIOS);
            stmt.execute(SQL_DROP_CREATE_PACIENTES);
            System.out.println("✅ Tablas creadas correctamente.");

            // Ejecutar inserts de prueba
            stmt.execute(PRUEBA_INSERTS);
            System.out.println("🧪 Datos de prueba insertados correctamente.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

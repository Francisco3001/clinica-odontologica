package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
    private static final String URL = "jdbc:h2:~/testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static final String SQL_CREATE_DOMICILIOS="CREATE TABLE DOMICILIOS (\n" +
            "                    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "                    calle VARCHAR(100) not null,\n" +
            "                    numero INT not null,\n" +
            "                    localidad VARCHAR(100) not null,\n" +
            "                    provincia VARCHAR(100) not null\n" +
            "                );";
    public static final String SQL_CREATE_PACIENTES="CREATE TABLE PACIENTES (\n" +
            "                    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "                    nombre VARCHAR(100) not null,\n" +
            "                    apellido VARCHAR(100) not null,\n" +
            "                    numero_contacto INT not null,\n" +
            "                    fecha_ingreso VARCHAR(20) not null,\n" +
            "                    email VARCHAR(100) not null,\n" +
            "                    domicilio_id INT not null,\n" +
            "                    FOREIGN KEY (domicilio_id) REFERENCES DOMICILIOS(id)\n" +
            "                );";

    public static final String SQL_CREATE_ODONTOLOGOS="CREATE TABLE ODONTOLOGOS (\n" +
            "                    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "                    nombre VARCHAR(100) not null,\n" +
            "                    apellido VARCHAR(100) not null,\n" +
            "                    matricula VARCHAR(20) not null\n" +
            "                );";

    public static final String PRUEBA_INSERTS = """
    -- Domicilios de prueba
    INSERT INTO DOMICILIOS (calle, numero, localidad, provincia) VALUES
    ('Av. Corrientes', 1234, 'CABA', 'Buenos Aires'),
    ('San Martín', 456, 'La Plata', 'Buenos Aires'),
    ('Belgrano', 789, 'Rosario', 'Santa Fe');

    -- Pacientes de prueba
    INSERT INTO PACIENTES (nombre, apellido, numero_contacto, fecha_ingreso, email, domicilio_id) VALUES
    ('Francisco', 'Noceda', 1134567890, '2025-10-09', 'fran@email.com', 1),
    ('Lucía', 'Gómez', 1122334455, '2025-09-15', 'lucia.gomez@gmail.com', 2),
    ('Martín', 'Pérez', 1144778899, '2025-10-01', 'martinperez@hotmail.com', 3);
    """;

    public static void crearTablas(){
        try (Connection conn = BD.getConnection();
             Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS PACIENTES;");
                stmt.execute("DROP TABLE IF EXISTS DOMICILIOS;");
                stmt.execute("DROP TABLE IF EXISTS ODONTOLOGOS;");
                stmt.execute(SQL_CREATE_DOMICILIOS);
                stmt.execute(SQL_CREATE_PACIENTES);
                stmt.execute(SQL_CREATE_ODONTOLOGOS);
                System.out.println("✅ Tablas creadas correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver H2: " + e.getMessage());
        }
        return conn;
    }
}

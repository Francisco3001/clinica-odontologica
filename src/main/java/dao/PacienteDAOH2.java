package dao;

import model.Paciente;
import model.Domicilio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements iDao<Paciente> {

    @Override
    public Paciente guardar(Paciente entidad) {
        String sql = "INSERT INTO PACIENTES (nombre, apellido, numero_contacto, fecha_ingreso, email, domicilio_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getApellido());
            ps.setInt(3, entidad.getNumeroContacto());
            ps.setString(4, entidad.getFechaIngreso());
            ps.setInt(6, entidad.getDomicilio().getId());
            ps.setString(5, entidad.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) entidad.setId(rs.getInt(1));

            System.out.println("✅ Paciente guardado correctamente: " + entidad.getNombre());

        } catch (SQLException e) {
            System.err.println("❌ Error al guardar paciente: " + e.getMessage());
        }
        return entidad;
    }

    @Override
    public Paciente buscar(Integer id) {
        String sql = "SELECT * FROM PACIENTES WHERE id = ?";
        Paciente paciente = null;

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DomicilioDAOH2 domicilioDAO = new DomicilioDAOH2();
                Domicilio domicilio = domicilioDAO.buscar(rs.getInt("domicilio_id"));

                paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("numero_contacto"),
                        rs.getString("fecha_ingreso"),
                        domicilio,
                        rs.getString("email")

                );
                System.out.println("🔍 Paciente encontrado: " + paciente.getNombre());
            } else {
                System.out.println("⚠️ No se encontró paciente con ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM PACIENTES WHERE id = ?";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("🗑️ Paciente eliminado correctamente (ID: " + id + ")");
            else
                System.out.println("⚠️ No se encontró paciente con ID " + id);

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar paciente: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Paciente entidad) {
        String sql = "UPDATE PACIENTES SET nombre=?, apellido=?, numero_contacto=?, fecha_ingreso=?, email=?, domicilio_id=? WHERE id=?";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getApellido());
            ps.setInt(3, entidad.getNumeroContacto());
            ps.setString(4, entidad.getFechaIngreso());
            ps.setInt(6, entidad.getDomicilio().getId());
            ps.setString(5, entidad.getEmail());
            ps.setInt(7, entidad.getId());

            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("♻️ Paciente actualizado correctamente: " + entidad.getNombre());
            else
                System.out.println("⚠️ No se encontró paciente con ID " + entidad.getId());

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar paciente: " + e.getMessage());
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        String sql = "SELECT * FROM PACIENTES";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = BD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                DomicilioDAOH2 domicilioDAO = new DomicilioDAOH2();
                Domicilio domicilio = domicilioDAO.buscar(rs.getInt("domicilio_id"));                Paciente paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("numero_contacto"),
                        rs.getString("fecha_ingreso"),
                        domicilio,
                        rs.getString("email")
                );
                pacientes.add(paciente);
            }

            System.out.println("📋 Se encontraron " + pacientes.size() + " pacientes en total.");

        } catch (SQLException e) {
            System.err.println("❌ Error al listar pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}

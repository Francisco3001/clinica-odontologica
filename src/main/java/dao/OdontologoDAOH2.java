package dao;

import model.Odontologo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo> {

    @Override
    public Odontologo guardar(Odontologo entidad) {
        String sql = "INSERT INTO ODONTOLOGOS (nombre, apellido, matricula) VALUES (?, ?, ?)";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getApellido());
            ps.setString(3, entidad.getMatricula());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidad.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar odontologo: " + e.getMessage());
        }

        return entidad;
    }

    @Override
    public Odontologo buscar(Integer id) {
        String sql = "SELECT * FROM ODONTOLOGOS WHERE id = ?";
        Odontologo odontologo = null;

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                odontologo = new Odontologo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("matricula")
                );
                System.out.println("🔍 Odontólogo encontrado: " + odontologo.getNombre());
            } else {
                System.out.println("⚠️ No se encontró odontólogo con ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar odontólogo: " + e.getMessage());
        }

        return odontologo;
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM ODONTOLOGOS WHERE id = ?";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("🗑️ Odontólogo eliminado correctamente (ID: " + id + ")");
            else
                System.out.println("⚠️ No se encontró odontólogo con ID " + id);

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar odontólogo: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Odontologo entidad) {
        String sql = "UPDATE ODONTOLOGOS SET nombre = ?, apellido = ?, matricula = ? WHERE id = ?";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getApellido());
            ps.setString(3, entidad.getMatricula());
            ps.setInt(4, entidad.getId());

            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("♻️ Odontólogo actualizado correctamente: " + entidad.getNombre());
            else
                System.out.println("⚠️ No se encontró odontólogo con ID " + entidad.getId());

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar odontólogo: " + e.getMessage());
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        String sql = "SELECT * FROM ODONTOLOGOS";
        List<Odontologo> odontologos = new ArrayList<>();

        try (Connection conn = BD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Odontologo odontologo = new Odontologo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("matricula")
                );
                odontologos.add(odontologo);
            }

            System.out.println("📋 Se encontraron " + odontologos.size() + " odontólogos en total.");

        } catch (SQLException e) {
            System.err.println("❌ Error al listar odontólogos: " + e.getMessage());
        }

        return odontologos;
    }
}

package dao;

import model.Domicilio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAOH2 implements iDao<Domicilio> {

    @Override
    public Domicilio guardar(Domicilio entidad) {
        String sql = "INSERT INTO DOMICILIOS (calle, numero, localidad, provincia) VALUES (?, ?, ?, ?)";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getCalle());
            ps.setInt(2, entidad.getNumero());
            ps.setString(3, entidad.getLocalidad());
            ps.setString(4, entidad.getProvincia());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entidad.setId(rs.getInt(1));
            }

            System.out.println("✅ Domicilio guardado correctamente: " + entidad.getCalle() + " " + entidad.getNumero());
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar domicilio: " + e.getMessage());
        }

        return entidad;
    }

    @Override
    public Domicilio buscar(Integer id) {
        String sql = "SELECT * FROM DOMICILIOS WHERE id = ?";
        Domicilio domicilio = null;

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                domicilio = new Domicilio(
                        rs.getInt("id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getString("localidad"),
                        rs.getString("provincia")
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar domicilio con ID " + id + ": " + e.getMessage());
        }

        return domicilio;
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM DOMICILIOS WHERE id = ?";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("🗑️ Domicilio eliminado con ID " + id);
            else
                System.out.println("⚠️ No se encontró un domicilio con ID " + id);

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar domicilio: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Domicilio entidad) {
        String sql = "UPDATE DOMICILIOS SET calle=?, numero=?, localidad=?, provincia=? WHERE id=?";

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entidad.getCalle());
            ps.setInt(2, entidad.getNumero());
            ps.setString(3, entidad.getLocalidad());
            ps.setString(4, entidad.getProvincia());
            ps.setInt(5, entidad.getId());

            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("✏️ Domicilio actualizado correctamente: " + entidad.getCalle());
            else
                System.out.println("⚠️ No se encontró domicilio con ID " + entidad.getId());

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar domicilio: " + e.getMessage());
        }
    }

    @Override
    public List<Domicilio> buscarTodos() {
        List<Domicilio> domicilios = new ArrayList<>();
        String sql = "SELECT * FROM DOMICILIOS";

        try (Connection conn = BD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Domicilio d = new Domicilio(
                        rs.getInt("id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getString("localidad"),
                        rs.getString("provincia")
                );
                domicilios.add(d);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar domicilios: " + e.getMessage());
        }

        return domicilios;
    }
}

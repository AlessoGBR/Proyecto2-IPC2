/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author alesso
 */
public class CrearAnuncio {

    private ConexionPool dataSource = ConexionPool.getInstance();

    public CrearAnuncio() {

    }

    public void crearAnuncioTexto(String texto, int id, Date fechaInicio, Date fechaFinal, double pago) throws SQLException {
        String sql = "INSERT INTO Anuncio (tipo, texto, nombre_anunciante, activo, fecha_inicio, fecha_final, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, "TEXTO");
                ps.setString(2, texto);
                ps.setInt(3, id);
                ps.setBoolean(4, false);
                ps.setDate(5, fechaInicio);
                ps.setDate(6, fechaFinal);
                ps.setDouble(7, pago);
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public void crearAnuncioImagenYTexto(String texto, String imagen, int id, Date fechaInicio, Date fechaFinal, double pago) throws SQLException {
        String sql = "INSERT INTO Anuncio (tipo, texto, path_imagen, nombre_anunciante, activo, fecha_inicio, fecha_final, pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, "IMAGEN Y TEXTO");
                ps.setString(2, texto);
                ps.setString(3, imagen);
                ps.setInt(4, id);
                ps.setBoolean(5, false);
                ps.setDate(6, fechaInicio);
                ps.setDate(7, fechaFinal);
                ps.setDouble(8, pago);
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public void crearAnuncioVideo(String url, int id, Date fechaInicio, Date fechaFinal, double pago) throws SQLException {
        String sql = "INSERT INTO Anuncio (tipo, url_video, nombre_anunciante, activo, fecha_inicio, fecha_final, pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, "VIDEO");
                ps.setString(2, url);
                ps.setInt(3, id);
                ps.setBoolean(4, false);
                ps.setDate(5, fechaInicio);
                ps.setDate(6, fechaFinal);
                ps.setDouble(7, pago);
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public int obtenerIdAnunciantePorNombre(String nombreAnunciante) throws SQLException {
        String sql = "SELECT idAnunciante FROM Anunciante WHERE nombre_usuario = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreAnunciante);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idAnunciante");
                } else {
                    throw new SQLException("Anunciante no encontrado.");
                }
            }
        }
    }

    public boolean ingresoCartera(String nombre, double montoTotal) {
        String sql = "UPDATE Anunciante SET cartera = ? WHERE nombre_usuario = ?";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, montoTotal);
                stmt.setString(2, nombre);
                int affectedRows = stmt.executeUpdate();
                connection.commit();
                return affectedRows > 0;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double obtenerCartera(String nombreUsuario) {
        String sql = "SELECT cartera FROM Anunciante WHERE nombre_usuario = ?";
        double cartera = 0;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartera = rs.getDouble("cartera");
                System.out.println(cartera);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }

        return cartera;
    }

    public boolean ingresoCarteraEditor(String nombre, int montoTotal) {
        String sql = "UPDATE Cartera SET cartera = ? WHERE nombre_usuario = ?";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, montoTotal);
                stmt.setString(2, nombre);
                int affectedRows = stmt.executeUpdate();
                connection.commit();
                return affectedRows > 0;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int obtenerCarteraEditor(String nombreUsuario) {
        String sql = "SELECT cartera FROM Cartera WHERE nombre_usuario = ?";
        int cartera = 0;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartera = rs.getInt("cartera");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }

        return cartera;
    }

}

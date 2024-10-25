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

    public void crearAnuncioTexto(String texto, int id, Date fechaInicio, Date fechaFinal) throws SQLException {
        String sql = "INSERT INTO Anuncio (tipo, texto, nombre_anunciante, activo, fecha_inicio, fecha_final) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "TEXTO");
            ps.setString(2, texto);
            ps.setInt(3, id);
            ps.setBoolean(4, false);
            ps.setDate(5, fechaInicio);
            ps.setDate(6, fechaFinal);
            ps.executeUpdate();
        }
    }

    public void crearAnuncioImagenYTexto(String texto, String imagen, int id, Date fechaInicio, Date fechaFinal) throws SQLException {
        String sql = "INSERT INTO Anuncio (tipo, texto, path_imagen, nombre_anunciante, activo, fecha_inicio, fecha_final) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "IMAGEN Y TEXTO");
            ps.setString(2, texto);
            ps.setString(3, imagen);
            ps.setInt(4, id);
            ps.setBoolean(5, false);
            ps.setDate(6, fechaInicio);
            ps.setDate(7, fechaFinal);
            ps.executeUpdate();
        }
    }

    public void crearAnuncioVideo(String url, int id, Date fechaInicio, Date fechaFinal) throws SQLException {
        String sql = "INSERT INTO Anuncio (tipo, url_video, nombre_anunciante, activo, fecha_inicio, fecha_final) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "VIDEO");
            ps.setString(2, url);
            ps.setInt(3, id);
            ps.setBoolean(4, false);
            ps.setDate(5, fechaInicio);
            ps.setDate(6, fechaFinal);
            ps.executeUpdate();
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

}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Anunciante;
import Models.Anuncio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alesso
 */
public class ObtenerAnuncios {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerAnuncios() {

    }

    public List<Anuncio> obtenerAnunciosAprobados(int nombre_usuario) {
        List<Anuncio> anuncios = new ArrayList<>();
        String query = "SELECT * FROM Anuncio WHERE nombre_anunciante = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nombre_usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Anuncio anuncio = new Anuncio();
                    anuncio.setIdAnuncio(rs.getInt("idAnuncio"));
                    anuncio.setTipo(rs.getString("tipo"));
                    anuncio.setTexto(rs.getString("texto"));
                    anuncio.setPathImagen(rs.getString("path_imagen"));
                    anuncio.setUrlVideo(rs.getString("url_video"));
                    anuncio.setFechaInicio(rs.getDate("fecha_inicio"));
                    anuncio.setFechaFinal(rs.getDate("fecha_final"));
                    anuncio.setPago(rs.getDouble("pago"));
                    anuncio.setActivo(rs.getBoolean("activo"));
                    anuncio.setNombreAnunciante(rs.getString("nombre_anunciante"));
                    anuncios.add(anuncio);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las revistas aprobadas: " + e.getMessage());
        }

        return anuncios;
    }

    public List<Anuncio> obtenerAnuncios() {
        List<Anuncio> anuncios = new ArrayList<>();
        String query = "SELECT * FROM Anuncio WHERE activo = false";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Anuncio anuncio = new Anuncio();
                anuncio.setIdAnuncio(rs.getInt("idAnuncio"));
                anuncio.setTipo(rs.getString("tipo"));
                anuncio.setFechaInicio(rs.getDate("fecha_inicio"));
                anuncio.setFechaFinal(rs.getDate("fecha_final"));
                anuncio.setPago(rs.getDouble("pago"));
                anuncio.setActivo(rs.getBoolean("activo"));
                anuncio.setNombreAnunciante(rs.getString("nombre_anunciante"));
                anuncios.add(anuncio);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        }

        return anuncios;
    }

    public List<Anuncio> obtenerAnunciosAprobados() {
        List<Anuncio> anuncios = new ArrayList<>();
        String query = "SELECT * FROM Anuncio WHERE activo = true";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Anuncio anuncio = new Anuncio();
                anuncio.setIdAnuncio(rs.getInt("idAnuncio"));
                anuncio.setTipo(rs.getString("tipo"));
                anuncio.setFechaInicio(rs.getDate("fecha_inicio"));
                anuncio.setFechaFinal(rs.getDate("fecha_final"));
                anuncio.setPago(rs.getDouble("pago"));
                anuncio.setActivo(rs.getBoolean("activo"));
                anuncio.setNombreAnunciante(rs.getString("nombre_anunciante"));
                anuncios.add(anuncio);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        }

        return anuncios;
    }

    public List<Anuncio> obtenerAnunciosCliente() {

        List<Anuncio> anuncios = new ArrayList<>();
        String query = "SELECT * FROM Anuncio WHERE activo = true";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Anuncio anuncio = new Anuncio();
                anuncio.setIdAnuncio(rs.getInt("idAnuncio"));
                anuncio.setTipo(rs.getString("tipo"));
                anuncio.setTexto(rs.getString("texto"));
                anuncio.setPathImagen(rs.getString("path_imagen"));
                anuncio.setUrlVideo(rs.getString("url_video"));
                anuncio.setFechaInicio(rs.getDate("fecha_inicio"));
                anuncio.setFechaFinal(rs.getDate("fecha_final"));
                anuncio.setPago(rs.getDouble("pago"));
                anuncio.setActivo(rs.getBoolean("activo"));
                anuncio.setNombreAnunciante(rs.getString("nombre_anunciante"));
                anuncios.add(anuncio);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        }

        return anuncios;
    }

    public int obtenerIdAnunciantePorNombre(String nombreAnunciante) {
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
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }
        return 0;
    }

    public boolean actualizarAceptacion(int idAnuncio) {
        String query = "UPDATE Anuncio SET activo = true WHERE idAnuncio = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idAnuncio);
            int rowsAffected = stmt.executeUpdate();

            actualizado = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la revista con ID " + idAnuncio + ": " + e.getMessage());
        }

        return actualizado;
    }

    public boolean actualizarDenegada(int idAnuncio) {
        String query = "UPDATE Anuncio SET activo = false WHERE idAnuncio = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idAnuncio);
            int rowsAffected = stmt.executeUpdate();

            actualizado = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la revista con ID " + idAnuncio + ": " + e.getMessage());
        }

        return actualizado;
    }
    
    public ArrayList<Anunciante> obtenerAnunciates() {
        ArrayList<Anunciante> anunciantes = new ArrayList<>();
        String query = "SELECT * FROM Anunciante;";
        try {
            Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                anunciantes.add(new Anunciante(resultado.getString("nombre_usuario"), resultado.getInt("idAnunciante")));
            }
        } catch (SQLException e) {
        }
        return anunciantes;
    }
}

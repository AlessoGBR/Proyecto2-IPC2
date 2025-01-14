/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.PrecioAnuncio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
public class ActualizarPreciosAnuncios {

    private ConexionPool dataSource = ConexionPool.getInstance();

    public ActualizarPreciosAnuncios() {

    }

    public boolean actualizar(PrecioAnuncio precio) {
        String sql = "UPDATE PrecioAnuncio SET texto = ?, imagen = ?, video = ? WHERE id = 1";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, precio.getTexto());
            stmt.setDouble(2, precio.getImagen());
            stmt.setDouble(3, precio.getVideo());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PrecioAnuncio obtenerPrecios() {
        String sql = "SELECT texto, imagen, video FROM PrecioAnuncio";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                PrecioAnuncio precios = new PrecioAnuncio();
                precios.setTexto(rs.getDouble("texto"));
                precios.setImagen(rs.getDouble("imagen"));
                precios.setVideo(rs.getDouble("video"));
                return precios;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentran precios
    }

}

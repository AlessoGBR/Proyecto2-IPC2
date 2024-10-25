/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Anuncio;
import Models.Revista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alesso
 */
public class ObtenerAnuncios {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerAnuncios() {

    }

    public List<Anuncio> obtenerRevistasAprobadas(int nombre_usuario) {
        List<Anuncio> anuncios = new ArrayList<>();
        String query = "SELECT * FROM Anuncio WHERE nombre_anunciante = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nombre_usuario);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las revistas aprobadas: " + e.getMessage());
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
}

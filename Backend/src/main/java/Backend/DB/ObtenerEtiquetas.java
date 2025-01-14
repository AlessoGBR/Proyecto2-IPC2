/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Anuncio;
import Models.Etiqueta;
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
public class ObtenerEtiquetas {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerEtiquetas() {

    }

    public List<Etiqueta> obtenerEtiquetas(String username) {
        List<Etiqueta> etiquetas = new ArrayList<>();
        String query = "SELECT nombre_etiqueta FROM Perfil_Etiquetas WHERE nombre_usuario = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Etiqueta etiqueta = new Etiqueta();
                    etiqueta.setNombre(rs.getString("nombre_etiqueta"));
                    etiquetas.add(etiqueta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener anuncios aprobados: " + e.getMessage());
        }

        return etiquetas;
    }

}

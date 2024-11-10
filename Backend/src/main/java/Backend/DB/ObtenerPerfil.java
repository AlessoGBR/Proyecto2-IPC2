/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
public class ObtenerPerfil {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerPerfil() {

    }

    public Usuario obtenerPerfil(String username) {
        String query = "SELECT * FROM Perfil WHERE nombre_usuario = ?;";
        Usuario user = null;

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setString(1, username);

            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    user = new Usuario();
                    user.setDescripcion(resultSet.getString("descripcion"));
                    user.setUsername(resultSet.getString("nombre_usuario"));
                    user.setFoto(resultSet.getString("fotoPerfil"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el perfil del usuario: " + e.getMessage());
        }

        return user;
    }

}

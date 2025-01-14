/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Comentario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alesso
 */
public class ObtenerComentario {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerComentario() {

    }

    public ArrayList<Comentario> obtenerComentariosRevista(int idRevista) {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        String query = "SELECT * FROM Comentario_Revista WHERE idRevista = ? ORDER BY fecha;";

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, idRevista);
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                int comentarioId = r.getInt(1);
                String comentarioTexto = obtenerComentario(comentarioId);
                String fecha = r.getDate(4).toString();
                String usuario = r.getString(2);
                int likes = r.getInt(3);

                comentarios.add(new Comentario(comentarioId, comentarioTexto, fecha, usuario, likes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    private String obtenerComentario(int idComentario) {

        String comentario = "";
        String query = "SELECT * FROM Comentario WHERE idComentario = ?;";

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, idComentario);
            ResultSet r = prepared.executeQuery();
            if (r.next()) {
                comentario = r.getString("comentario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentario;
    }

    public int registrarComentario(Comentario comentario) {
        String query = "INSERT INTO Comentario(comentario, fecha, nombre_usuario) VALUES(?,CURRENT_DATE,?);";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepared.setString(1, comentario.getComentario());
            prepared.setString(2, comentario.getNombreUsuario());
            prepared.executeUpdate();
            ResultSet r = prepared.getGeneratedKeys();
            int n = -1;
            while (r.next()) {
                n = r.getInt(1);
            }
            return n;
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }
        return -1;
    }

    public boolean registrarComentarioRevista(Comentario comentario) {
        String query = "INSERT INTO Comentario_Revista(idComentario, nombre_usuario, idRevista, fecha) VALUES(?,?,?,CURRENT_DATE);";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, comentario.getIdComentario());
            prepared.setString(2, comentario.getNombreUsuario());
            prepared.setInt(3, comentario.getIdRevista());
            prepared.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
            return false;
        }
    }

    public boolean verificarComentarios(int idRevista) {
        String query = "SELECT comentarios FROM Revista WHERE idRevista = ?;";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, idRevista);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("comentarios");
                } else {
                    System.err.println("No se encontró la revista con id: " + idRevista);
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al verificar comentarios: " + ex.getMessage());
            return false;
        }
    }

}

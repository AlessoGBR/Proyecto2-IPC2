/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Comentario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alesso
 */
public class ObtenerComentario {

    private final ConexionPool dataSource = ConexionPool.getInstance();
    
    public ObtenerComentario(){
    
    }
    
     public ArrayList<Comentario> obtenerComentariosRevista(int idRevista) {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        String query = "SELECT * FROM Comentario_Revista WHERE idRevista = ? ORDER BY fecha;";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
             
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
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {
             
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

}

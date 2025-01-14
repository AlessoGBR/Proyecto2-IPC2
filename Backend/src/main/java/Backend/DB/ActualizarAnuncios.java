/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
public class ActualizarAnuncios {
    
    private final ConexionPool dataSource = ConexionPool.getInstance();
    
    public ActualizarAnuncios(){
    
    }
    
    public boolean actualizarPrecio(int idAnuncio, double precio) {
        String query = "UPDATE Anuncio SET pago = ? WHERE idAnuncio = ?";
        boolean actualizado = false;
        System.out.println(idAnuncio);
        System.out.println(precio);
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, precio);
            stmt.setInt(2, idAnuncio);
            int rowsAffected = stmt.executeUpdate();
            
            actualizado = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la revista con ID " + idAnuncio + ": " + e.getMessage());
        }

        return actualizado;
    }
}

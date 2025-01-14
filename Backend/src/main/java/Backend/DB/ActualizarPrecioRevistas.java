/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.PrecioRevista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
public class ActualizarPrecioRevistas {
    
    private ConexionPool dataSource = ConexionPool.getInstance();
    
    public ActualizarPrecioRevistas(){
    
    }
    
    public boolean actualizar(PrecioRevista precio) {
        String sql = "UPDATE PrecioRevista SET precio = ? WHERE id = 1";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, precio.getPrecio());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PrecioRevista obtenerPrecios() {
        String sql = "SELECT precio FROM PrecioRevista";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                PrecioRevista precios = new PrecioRevista();
                precios.setPrecio(rs.getDouble("precio"));
                return precios;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentran precios
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Suscripcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
public class CrearSuscripcion {

    private ConexionPool dataSource = ConexionPool.getInstance();

    public CrearSuscripcion() {

    }

    public boolean ingresoSuscripcion(Suscripcion suscripcion) {
        String query = "INSERT INTO Suscripcion(fecha, nombre_usuario, idRevista) VALUES(CURRENT_DATE,?,?);";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false); 
            try (PreparedStatement prepared = connection.prepareStatement(query)) {
                prepared.setString(1, suscripcion.getNombreUsuario());
                prepared.setInt(2, suscripcion.getIdRevista());
                prepared.executeUpdate();
                connection.commit(); 
                return true;
            } catch (SQLException ex) {
                connection.rollback(); 
                System.out.println("Error al ingresar suscripción: " + ex);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al ingresar suscripción: " + e);
            return false;
        }
    }

    public boolean verificarSuscripcion(String nombreUsuario, String idRevista) {
        String query = "SELECT COUNT(*) FROM Suscripcion WHERE nombre_usuario = ? AND idRevista = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, idRevista);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la suscripción: " + e.getMessage());
        }
        return false;
    }

    public boolean anularSuscripcion(Suscripcion suscripcion) {
        String query = "DELETE FROM Suscripcion WHERE idRevista = ? AND nombre_usuario = ?;";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false); 
            try (PreparedStatement prepared = connection.prepareStatement(query)) {
                prepared.setInt(1, suscripcion.getIdRevista());
                prepared.setString(2, suscripcion.getNombreUsuario());
                int rowsAffected = prepared.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback(); 
                    return false;
                }
            } catch (SQLException ex) {
                connection.rollback(); 
                System.out.println("Error al anular suscripción: " + ex);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión al anular suscripción: " + e);
            return false;
        }
    }
}

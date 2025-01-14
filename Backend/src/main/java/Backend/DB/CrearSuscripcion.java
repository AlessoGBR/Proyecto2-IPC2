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

    public boolean suscripcion(int idRevista) {
        String query = "SELECT suscripciones FROM Revista WHERE idRevista = ?;";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, idRevista);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("suscripciones");
                } else {
                    System.err.println("No se encontró la revista con id: " + idRevista);
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al verificar suscripciones: " + ex.getMessage());
            return false;
        }
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
<<<<<<< HEAD
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
=======

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, suscripcion.getIdRevista());
            prepared.setString(2, suscripcion.getNombreUsuario());

            int rowsAffected = prepared.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.out.println("Error al anular suscripción: " + ex);
>>>>>>> 3a20f4d7016bcaced155fd07b5058f366e2b6a72
            return false;
        }
    }
}

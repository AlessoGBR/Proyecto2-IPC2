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
public class ActualizarRevista {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ActualizarRevista() {

    }

    public boolean actualizarEstadoComentarios(int idRevista, boolean comentariosActivos) {
        String query = "UPDATE Revista SET comentarios = ? WHERE idRevista = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            stmt.setBoolean(1, comentariosActivos);
            stmt.setInt(2, idRevista);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                actualizado = true;
            }

            connection.commit(); 
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = dataSource.getConnection()) {
                connection.rollback(); 
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return actualizado;
    }

    public boolean actualizarEstadoMeGusta(int idRevista, boolean megustaActivos) {
        String query = "UPDATE Revista SET reacciones = ? WHERE idRevista = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            stmt.setBoolean(1, megustaActivos);
            stmt.setInt(2, idRevista);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                actualizado = true;
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = dataSource.getConnection()) {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return actualizado;
    }

    public boolean actualizarEstadoSuscripciones(int idRevista, boolean suscripcionesActivados) {
        String query = "UPDATE Revista SET suscripciones = ? WHERE idRevista = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            stmt.setBoolean(1, suscripcionesActivados);
            stmt.setInt(2, idRevista);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                actualizado = true;
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection connection = dataSource.getConnection()) {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return actualizado;
    }

    public boolean actualizarAceptacion(int idRevista, double precio) {
        String query = "UPDATE Revista SET aprobada = true, denegada = false, precio = ? WHERE idRevista = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            stmt.setDouble(1, precio);
            stmt.setInt(2, idRevista);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                actualizado = true;
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la revista con ID " + idRevista + ": " + e.getMessage());
            try (Connection connection = dataSource.getConnection()) {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return actualizado;
    }

    public boolean actualizarDenegada(int idRevista, double precio) {
        String query = "UPDATE Revista SET aprobada = false, denegada = true, precio = ? WHERE idRevista = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            connection.setAutoCommit(false);

            stmt.setDouble(1, precio);
            stmt.setInt(2, idRevista);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                actualizado = true;
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error al actualizar la revista con ID " + idRevista + ": " + e.getMessage());
            try (Connection connection = dataSource.getConnection()) {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }

        return actualizado;
    }
    
    public boolean actualizarPrecio(int idRevista, double precio) {
        String query = "UPDATE Revista SET precio = ? WHERE idRevista = ?";
        boolean actualizado = false;

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, precio);
            stmt.setInt(2, idRevista);
            int rowsAffected = stmt.executeUpdate();

            actualizado = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la revista con ID " + idRevista + ": " + e.getMessage());
        }

        return actualizado;
    }
}

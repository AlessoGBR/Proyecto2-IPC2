/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Reaccion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alesso
 */
public class ObtenerReacciones {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerReacciones() {

    }

    public ArrayList<Reaccion> obtenerReaccionesPorRevista(int idRevista) {
        ArrayList<Reaccion> reacciones = new ArrayList<>();
        String query = "SELECT * FROM Reaccion_Revista WHERE idRevista = ?;";

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, idRevista);
            try (ResultSet resultSet = prepared.executeQuery()) {
                while (resultSet.next()) {
                    int idReaccion = resultSet.getInt(1);
                    boolean reaccion = obtenerReaccion(idReaccion);
                    String fecha = resultSet.getDate(4).toString();
                    String usuario = resultSet.getString(3);
                    int idRev = resultSet.getInt(2);

                    reacciones.add(new Reaccion(idReaccion, reaccion, fecha, usuario, idRev));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reacciones;
    }

    public boolean obtenerReaccion(int idReaccion) {
        String query = "SELECT * FROM Reaccion WHERE idReaccion = ?;";

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, idReaccion);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("reacciones");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void darMG(Reaccion reaccion) {
        Reaccion nueva = verificarMG(reaccion);
        if (nueva != null) {
            cambiarValorMG(reaccion);
        } else {
            int generado = registrarMG(reaccion);
            if (generado != -1) {
                reaccion.setIdReaccion(generado);
                registrarMGRevista(reaccion);
            } else {
                System.err.println("No se pudo registrar la reacción.");
            }
        }
    }

    public boolean verificarMegusta(int idRevista) {
        String query = "SELECT reacciones FROM Revista WHERE idRevista = ?;";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {
            prepared.setInt(1, idRevista);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("reacciones");
                } else {
                    System.err.println("No se encontró la revista con id: " + idRevista);
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al verificar reacciones: " + ex.getMessage());
            return false;
        }
    }

    private Reaccion verificarMG(Reaccion reaccion) {
        Reaccion reac = null;
        String query = "SELECT * FROM Reaccion_Revista WHERE idRevista = ? AND nombre_usuario = ?;";

        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, reaccion.getIdRevista());
            prepared.setString(2, reaccion.getNombreUsuario());

            try (ResultSet r = prepared.executeQuery()) {
                if (r.next()) {
                    reac = new Reaccion(
                            r.getInt("idReaccion"),
                            obtenerReaccion(r.getInt("idReaccion")),
                            r.getDate("fecha").toString(),
                            r.getString("nombre_usuario"),
                            r.getInt("idRevista")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la reacción: " + e.getMessage());
        }

        return reac;
    }

    private int registrarMG(Reaccion reaccion) {
        String query = "INSERT INTO Reaccion(reacciones, fecha, nombre_usuario) VALUES(?,?,?);";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            prepared.setBoolean(1, true);
            Date fechaNueva = Date.valueOf(reaccion.getFecha());
            prepared.setDate(2, fechaNueva);
            prepared.setString(3, reaccion.getNombreUsuario());
            prepared.executeUpdate();
            ResultSet r = prepared.getGeneratedKeys();
            if (r.next()) {
                return r.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println("Error al registrar la reacción: " + ex.getMessage());
        }
        return -1;
    }

    private void registrarMGRevista(Reaccion reaccion) {
        String query = "INSERT INTO Reaccion_Revista(idReaccion, nombre_usuario, idRevista, fecha) VALUES(?,?,?,?);";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, reaccion.getIdReaccion());
            Date fechaNueva = Date.valueOf(reaccion.getFecha());
            prepared.setString(2, reaccion.getNombreUsuario());
            prepared.setInt(3, reaccion.getIdRevista());
            prepared.setDate(4, fechaNueva);
            prepared.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al registrar la reacción en la revista: " + ex.getMessage());
        }
    }

    private void cambiarValorMG(Reaccion reaccion) {
        String query = "UPDATE Reaccion SET reacciones = ?, fecha = ? WHERE idReaccion = ?;";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setBoolean(1, reaccion.isReaccion());
            Date fechaNueva = Date.valueOf(reaccion.getFecha());
            prepared.setDate(2, fechaNueva);
            prepared.setInt(3, reaccion.getIdReaccion());
            prepared.executeUpdate();
            cambiarFechaMG(reaccion);
        } catch (SQLException ex) {
            System.err.println("Error al cambiar el valor de la reacción: " + ex.getMessage());
        }
    }

    private void cambiarFechaMG(Reaccion reaccion) {
        String query = "UPDATE Reaccion_Revista SET fecha = ? WHERE idReaccion = ?;";
        try (Connection connection = dataSource.getConnection(); PreparedStatement prepared = connection.prepareStatement(query)) {

            Date fechaNueva = Date.valueOf(reaccion.getFecha());
            prepared.setDate(1, fechaNueva);
            prepared.setInt(2, reaccion.getIdReaccion());
            prepared.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al cambiar la fecha de la reacción en la revista: " + ex.getMessage());
        }
    }
}

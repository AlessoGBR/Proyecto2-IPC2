/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Etiqueta;
import Models.Revista;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author alesso
 */
public class CrearRevista {

    private ConexionPool dataSource = ConexionPool.getInstance();

    private final Revista revista;

    public CrearRevista(Revista revista) {
        this.revista = revista;
    }

    public boolean crearRevista() {

        if (insertarRevista()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean insertarRevista(){

        String insertSQL = "INSERT INTO Revista (revista ,titulo, descripcion, no_version, aprobada, suscripciones, comentarios, reacciones, fecha, nombre_usuario, denegada) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;        
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
                String fechaLocal = revista.getFecha();
                Date fechaSql = Date.valueOf(fechaLocal);

                stmt.setString(1, "ninguno");
                stmt.setString(2, revista.getTitulo());
                stmt.setString(3, revista.getDescripcion());
                stmt.setString(4, revista.getVersion());
                stmt.setBoolean(5, false);  // No aprobada inicialmente
                stmt.setBoolean(6, revista.isSuscripciones());
                stmt.setBoolean(7, revista.isTieneComentarios());
                stmt.setBoolean(8, revista.isTieneReacciones());
                stmt.setDate(9, fechaSql);
                stmt.setString(10, revista.getusuario());
                stmt.setBoolean(11, false);
                stmt.executeUpdate();
            }

            int idRevista = obtenerIdRevista(connection, revista.getTitulo());
            ingresarEtiquetas(connection, idRevista, revista.getEtiquetas());

            connection.commit();
            System.out.println("ingreso si");
            return true;

        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException rollbackEx) {
                        try {
                            throw new SQLException("Error al deshacer la transacción: " + rollbackEx.getMessage(), rollbackEx);
                        } catch (SQLException ex1) {
                            System.out.println("ingreso no rollback");
                        }
                    }
                }
                throw new SQLException("Error al insertar la revista: " + ex.getMessage(), ex);
            } catch (SQLException ex1) {
                System.out.println(" ingreso no exception" + ex.getMessage());
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    try {
                        throw new SQLException("Error al cerrar la conexión: " + e.getMessage(), e);
                    } catch (SQLException ex) {
                        System.out.println("ingeso no connection"+ ex.getMessage());
                    }
                }
            }
        }
        return false;
    }

    public void ingresarEtiquetas(Connection connection, int idRevista, List<Etiqueta> etiquetas) throws SQLException {
        String sqlInsertRel = "INSERT INTO Etiqueta_Revista (nombre_etiqueta,idRevista) VALUES (?, ?)";

        try (PreparedStatement psInsertRel = connection.prepareStatement(sqlInsertRel)) {
            for (Etiqueta etiqueta : etiquetas) {
                psInsertRel.setString(1, etiqueta.getNombre());
                psInsertRel.setInt(2, idRevista);
                psInsertRel.addBatch();
            }
            psInsertRel.executeBatch();
        }
    }

    public int obtenerIdRevista(Connection connection, String nombreRevista) throws SQLException {
        String sql = "SELECT idRevista FROM Revista WHERE titulo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreRevista);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idRevista");
                } else {
                    throw new SQLException("No se encontró la revista con el título proporcionado.");
                }
            }
        }
    }

}

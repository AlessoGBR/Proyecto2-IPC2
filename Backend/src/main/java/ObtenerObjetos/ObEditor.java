/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObtenerObjetos;

import Backend.DB.ConexionPool;
import Models.Comentario;
import Models.Etiqueta;
import Models.Reaccion;
import Models.Revista;
import Models.RevistaReport;
import Models.Suscripcion;
import Models.SuscriptorRevista;
import Models.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alesso
 */
public class ObEditor {

    private ConexionPool dataSource = ConexionPool.getInstance();
    private ObGeneral obtenerG = new ObGeneral();
    private Connection connection;

    public ObEditor() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }
    }

    public Revista obtenerRevistaID(int idRevista) {
        Revista revista = null;
        String query = "SELECT * FROM Revista WHERE idRevista = ?;";
        try (PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, idRevista);
            ResultSet r = prepared.executeQuery();

            if (r.next()) {
                String titulo = r.getString("titulo");
                String descripcion = r.getString("descripcion");
                int version = r.getInt("no_version");
                String fecha = r.getDate("fecha").toString();
                boolean suscripciones = r.getBoolean("suscripciones");
                boolean tieneComentarios = r.getBoolean("comentarios");
                boolean tieneReacciones = r.getBoolean("reacciones");
                Usuario usuario = obtenerG.obtenerUsuario(r.getString("nombre_usuario"));
                List<Etiqueta> etiquetas = obtenerG.obtenerEtiquetas(1, r.getInt("idRevista"));
                boolean aprobado = r.getBoolean("aprobada");
                boolean denegada = r.getBoolean("denegada");
                double precio = r.getDouble("precio");
                boolean tieneAnuncios = r.getBoolean("anuncios");

                revista = new Revista(titulo, descripcion, version, fecha, suscripciones,
                        tieneComentarios, tieneReacciones,tieneAnuncios, usuario.getUsername(), etiquetas, aprobado, denegada, precio);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la revista con ID " + idRevista + ": " + e.getMessage());
        }

        return revista;
    }

    public List<RevistaReport> obtenerRevistasMasGustadas(int idRevista, String usuario, String fecha1, String fecha2) {

        ArrayList<RevistaReport> revistas = new ArrayList<>();
        String query = "SELECT idRevista, COUNT(idRevista) as total FROM Reaccion_Revista GROUP BY idRevista ORDER BY total DESC LIMIT 5;";

        // Consulta para obtener las 5 revistas más gustadas en un rango de fechas
        String consultaR = "SELECT idRevista, COUNT(idRevista) as total FROM Reaccion_Revista WHERE fecha BETWEEN ? AND ? GROUP BY idRevista ORDER BY total DESC LIMIT 5;";

        // Consultas para obtener las reacciones, comentarios y suscripciones de cada revista
        String query3 = "SELECT * FROM Reaccion_Revista WHERE idRevista = ?;";
        String query4 = "SELECT * FROM Reaccion_Revista WHERE idRevista = ? AND fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = null;
            if (fechasVacias(fecha1, fecha2) && idRevista == 0) {
                prepared = connection.prepareStatement(query);
            } else if (!fechasVacias(fecha1, fecha2) && idRevista == 0) {
                prepared = connection.prepareStatement(consultaR);
                prepared.setDate(1, getDate(fecha1));
                prepared.setDate(2, getDate(fecha2));
            } else if (fechasVacias(fecha1, fecha2) && idRevista != 0) {
                prepared = connection.prepareStatement(query3);
                prepared.setInt(1, idRevista);
            } else if (!fechasVacias(fecha1, fecha2) && idRevista != 0) {
                prepared = connection.prepareStatement(query4);
                prepared.setInt(1, idRevista);
                prepared.setDate(2, getDate(fecha1));
                prepared.setDate(3, getDate(fecha2));
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                Revista revista = obtenerRevistaID(r.getInt(1));
                if (revista.getusuario().equalsIgnoreCase(usuario)) {
                    revistas.add(new RevistaReport(revista, obtenerComentariosConsulta(r.getInt(1), fecha1, fecha2),
                            obtenerReaccionesConsulta(r.getInt(1), fecha1, fecha2), obtenerSuscripcionConsulta(r.getInt(1), fecha1, fecha2)));
                }
            }
        } catch (SQLException e) {
        }
        return revistas;
    }

    public List<RevistaReport> obtenerRevistasReporte(String usuario, String fecha_inicio, String fecha_final, int idRevista, int opcion) {

        String query2 = "SELECT idRevista FROM Revista WHERE idRevista = ? AND nombre_usuario = ?;";
        return obtenerRevistas(query2, idRevista, usuario, fecha_inicio, fecha_final);
    }

    private List<RevistaReport> obtenerRevistas(String consultaR, int idRevista, String usuario, String fecha1, String fecha2) {
        ArrayList<RevistaReport> revistas = new ArrayList<>();
        String query = "SELECT idRevista FROM Revista WHERE nombre_usuario = ?;";
        try {
            PreparedStatement prepared = null;
            if (idRevista == 0) {
                prepared = connection.prepareStatement(query);
                prepared.setString(1, usuario);
            } else {
                prepared = connection.prepareStatement(consultaR);
                prepared.setInt(1, idRevista);
                prepared.setString(2, usuario);
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                revistas.add(new RevistaReport(obtenerRevistaID(r.getInt(1)), obtenerComentariosConsulta(r.getInt(1), fecha1, fecha2),
                        obtenerReaccionesConsulta(r.getInt(1), fecha1, fecha2), obtenerSuscripcionConsulta(r.getInt(1), fecha1, fecha2)));
            }
        } catch (SQLException e) {
        }
        return revistas;
    }

    public ArrayList<Comentario> obtenerComentariosConsulta(int idRevista, String fecha_inicio, String fecha_final) {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        String query = "SELECT * FROM Comentario_Revista WHERE idRevista = ? ORDER BY fecha;";
        String com2 = "SELECT * FROM Comentario_Revista WHERE idRevista = ? AND fecha BETWEEN ? AND ?";
        try {
            PreparedStatement prepared = null;
            if (fechasVacias(fecha_final, fecha_final)) {
                prepared = connection.prepareStatement(query);
                prepared.setInt(1, idRevista);
            } else {
                prepared = connection.prepareStatement(com2);
                prepared.setInt(1, idRevista);
                Date f = Date.valueOf(fecha_inicio);
                prepared.setDate(2, f);
                Date i = Date.valueOf(fecha_final);
                prepared.setDate(3, i);
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                comentarios.add(new Comentario(r.getInt(1), obtenerComentario(r.getInt(1)), r.getDate(4).toString(), r.getString(2), r.getInt(3)));
            }
        } catch (SQLException e) {
        }
        return comentarios;
    }

    public ArrayList<Reaccion> obtenerReaccionesConsulta(int idRevista, String fecha1, String fecha2) {
        ArrayList<Reaccion> reacciones = new ArrayList<>();
        String query = "SELECT * FROM Reaccion_Revista WHERE idRevista = ?;";
        String query2 = "SELECT * FROM Reaccion_Revista WHERE idRevista = ? AND fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = null;
            if (fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query);
                prepared.setInt(1, idRevista);
            } else {
                prepared = connection.prepareStatement(query2);
                prepared.setInt(1, idRevista);
                Date f = Date.valueOf(fecha1);
                prepared.setDate(2, f);
                Date i = Date.valueOf(fecha2);
                prepared.setDate(3, i);
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                if (obtenerReaccion(r.getInt(1))) {
                    reacciones.add(new Reaccion(r.getInt(1), r.getDate(4).toString(), r.getString(3), r.getInt(2)));
                }
            }
        } catch (SQLException e) {
        }
        return reacciones;
    }

    public String obtenerComentario(int idComentario) {
        String comentario = "";
        String query = "SELECT * FROM Comentario WHERE idComentario = ?;";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setInt(1, idComentario);
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                return r.getString("comentario");
            }
        } catch (SQLException e) {
        }
        return comentario;
    }

    public ArrayList<Suscripcion> obtenerSuscripcionConsulta(int idRevista, String fecha1, String fecha2) {
        ArrayList<Suscripcion> sus = new ArrayList<>();
        String query = "SELECT * FROM Suscripcion WHERE idRevista = ?;";
        String query2 = "SELECT * FROM Suscripcion WHERE idRevista = ? AND fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = null;
            if (fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query);
                prepared.setInt(1, idRevista);
            } else {
                prepared = connection.prepareStatement(query2);
                prepared.setInt(1, idRevista);
                Date f = Date.valueOf(fecha1);
                prepared.setDate(2, f);
                Date i = Date.valueOf(fecha2);
                prepared.setDate(3, i);
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                sus.add(new Suscripcion(r.getInt(1), r.getString(2), r.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return sus;
    }

    public boolean obtenerReaccion(int idReaccion) {
        String query = "SELECT * FROM Reaccion WHERE idReaccion = ?;";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setInt(1, idReaccion);
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                return r.getBoolean("reacciones");
            }
        } catch (SQLException e) {
        }
        return false;
    }

    private boolean fechasVacias(String fecha1, String fecha2) {
        return fecha1.equalsIgnoreCase("Vacio") && fecha2.equalsIgnoreCase("vacio");
    }

    private Date getDate(String fecha) {
        Date f = Date.valueOf(fecha);
        return f;
    }

}

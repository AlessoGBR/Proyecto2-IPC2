/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObtenerObjetos;

import Backend.DB.ConexionPool;
import Models.Anunciante;
import Models.AnuncianteReport;
import Models.Anuncio;
import Models.RevistaReport;
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
public class ObAdmin {

    private ConexionPool dataSource = ConexionPool.getInstance();
    private Connection connection;

    public ObAdmin(){
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }
    }

    public List<RevistaReport> obtenerRevistasReporte(String fecha_inicio, String fecha_final, int idRevista, int opcion) {
        String query2 = "SELECT idRevista FROM Revista WHERE idRevista = ?;";
        return obtenerRevistas(query2, idRevista, fecha_inicio, fecha_final);
    }

    private List<RevistaReport> obtenerRevistas(String consultaR, int idRevista, String fecha1, String fecha2) {
        ObEditor ob = new ObEditor();
        ArrayList<RevistaReport> revistas = new ArrayList<>();
        String query = "SELECT idRevista FROM Revista;";
        try {
            PreparedStatement prepared = null;
            if (idRevista == 0) {
                prepared = connection.prepareStatement(query);
            } else {
                prepared = connection.prepareStatement(consultaR);
                prepared.setInt(1, idRevista);
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                revistas.add(new RevistaReport(ob.obtenerRevistaID(r.getInt(1)), ob.obtenerComentariosConsulta(r.getInt(1), fecha1, fecha2),
                        ob.obtenerReaccionesConsulta(r.getInt(1), fecha1, fecha2), ob.obtenerSuscripcionConsulta(r.getInt(1), fecha1, fecha2)));
            }
        } catch (SQLException e) {
        }
        return revistas;
    }

    public List<RevistaReport> obtenerRevistasPopulares(int opcion, String fecha1, String fecha2) {
        ObEditor ob = new ObEditor();
        ArrayList<RevistaReport> revistas = new ArrayList<>();
        String query = "SELECT idRevista,COUNT(idRevista) As veces FROM Suscripcion GROUP BY idRevista ORDER BY veces DESC LIMIT 5;";
        String query2 = "SELECT idRevista, COUNT(idRevista) As veces FROM Comentario_Revista GROUP BY idRevista ORDER BY veces DESC LIMIT 5;";
        String query3 = "SELECT idRevista,COUNT(idRevista) As veces FROM Suscripcion WHERE fecha BETWEEN ? AND ? GROUP BY idRevista ORDER BY veces DESC LIMIT 5;";
        String query4 = "SELECT idRevista, COUNT(idRevista) As veces FROM Comentario_Revista WHERE fecha BETWEEN ? AND ? GROUP BY idRevista ORDER BY veces DESC LIMIT 5;";
        try {
            PreparedStatement prepared = null;

            if (opcion == 1 && fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query);
            } else if (opcion == 2 && fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query2);
            } else if (opcion == 1 && !fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query3);
            } else if (opcion == 2 && !fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query4);
            }
            if (!fechasVacias(fecha1, fecha2)) {
                prepared.setDate(1, getDate(fecha1));
                prepared.setDate(2, getDate(fecha2));
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                revistas.add(new RevistaReport(ob.obtenerRevistaID(r.getInt(1)), ob.obtenerComentariosConsulta(r.getInt(1), fecha1, fecha2),
                        ob.obtenerReaccionesConsulta(r.getInt(1), fecha1, fecha2), ob.obtenerSuscripcionConsulta(r.getInt(1), fecha1, fecha2)));
            }
        } catch (SQLException e) {
        }
        return revistas;
    }

    public List<AnuncianteReport> obtenerAnunciantesReporte(String fecha1, String fecha2, String anunciante) {
        List<AnuncianteReport> a = new ArrayList<>();
        String query1 = "SELECT * FROM Anunciante;";
        String query2 = "SELECT * FROM Anunciante WHERE nombre_usuario = ?;";
        try {
            PreparedStatement prepared = null;
            if (anunciante.equalsIgnoreCase("vacio")) {
                prepared = connection.prepareStatement(query1);
            } else {
                prepared = connection.prepareStatement(query2);
                prepared.setString(1, anunciante);
            }
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                a.add(new AnuncianteReport(obtenerAnunciante(r.getString("nombre_usuario")),
                        obtenerAnunciosSegunAnunciante(r.getString("nombre_usuario"), fecha1, fecha2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return a;
    }

    public ArrayList<Anuncio> obtenerAnunciosSegunAnunciante(String anunciante, String fecha1, String fecha2) {
        ArrayList<Anuncio> anuncios = new ArrayList<>();
        String query1 = "SELECT a.* FROM Anuncio a "
                + "JOIN Anunciante an ON a.nombre_anunciante = an.idAnunciante "
                + "WHERE an.nombre_usuario = ?;";
        String query2 = "SELECT a.* FROM Anuncio a "
                + "JOIN Anunciante an ON a.nombre_anunciante = an.idAnunciante "
                + "WHERE a.fecha_inicio BETWEEN ? AND ? AND an.nombre_usuario = ?;";


        PreparedStatement prepared = null;
        try {
            if (fechasVacias(fecha1, fecha2)) {
                prepared = connection.prepareStatement(query1);
                prepared.setString(1, anunciante);
            } else {
                prepared = connection.prepareStatement(query2);
                prepared.setDate(1, getDate(fecha1));
                prepared.setDate(2, getDate(fecha2));
                prepared.setString(3, anunciante);
            }

            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                anuncios.add(new Anuncio(
                        r.getInt(1), // id
                        r.getString(2), // texto
                        r.getString(3), // video
                        r.getString(4), // imagen
                        r.getBoolean(5),// estado
                        r.getDate(6), // fecha inicio
                        r.getDate(7), // fecha fin
                        r.getDouble(8), // pago
                        r.getString(9),// anunciante
                        r.getString(10) // tipo
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener anuncios: " + e.getMessage());
        } finally {
            try {
                if (prepared != null) {
                    prepared.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }

        return anuncios;
    }

    public Anunciante obtenerAnunciante(String nombre) {
        String query = "SELECT * FROM Anunciante WHERE nombre_usuario = ?";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, nombre);
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                return new Anunciante(r.getString(3), r.getInt(1));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    private boolean fechasVacias(String fecha1, String fecha2) {
        return fecha1.equalsIgnoreCase("Vacio") && fecha2.equalsIgnoreCase("vacio");
    }

    private Date getDate(String fecha) {
        Date f = Date.valueOf(fecha);
        return f;
    }
}

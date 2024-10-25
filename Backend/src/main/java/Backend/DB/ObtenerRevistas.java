/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Etiqueta;
import Models.Revista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alesso
 */
public class ObtenerRevistas {

    private ConexionPool dataSource = ConexionPool.getInstance();

    public ObtenerRevistas() {
    }

    public List<Revista> obtenerRevistasDelEditor(String nombreUsuario) {
        List<Revista> revistas = new ArrayList<>();
        String query = "SELECT  idRevista, titulo, descripcion, no_version, aprobada, comentarios, reacciones, suscripciones, denegada FROM Revista WHERE nombre_usuario = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, nombreUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setIdRevista(rs.getInt("idRevista"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setDescripcion(rs.getString("descripcion"));
                    revista.setVersion(rs.getString("no_version"));
                    revista.setAprobada(rs.getBoolean("aprobada"));
                    revista.setTieneComentarios(rs.getBoolean("comentarios"));
                    revista.setTieneReacciones(rs.getBoolean("reacciones"));
                    revista.setSuscripciones(rs.getBoolean("suscripciones"));
                    revista.setDenegada(rs.getBoolean("denegada"));

                    revistas.add(revista);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }

    public List<Revista> obtenerRevistasAprobadas() {
        List<Revista> revistasAprobadas = new ArrayList<>();
        String query = "SELECT * FROM Revista WHERE aprobada = 1";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setIdRevista(rs.getInt("idRevista"));
                    revista.setrevistaPath(rs.getString("revista"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setDescripcion(rs.getString("descripcion"));
                    revista.setVersion(rs.getString("no_version"));
                    revista.setFecha(rs.getString("fecha"));
                    revistasAprobadas.add(revista);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las revistas aprobadas: " + e.getMessage());
        }

        return revistasAprobadas;
    }

    public List<Revista> buscarRevistasPorEtiquetas(List<Etiqueta> etiquetas) {
        List<Revista> revistas = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT DISTINCT r.idRevista, r.revista, r.titulo, r.descripcion, r.no_version ")
                .append("FROM Revista r ")
                .append("JOIN Etiqueta_Revista er ON r.idRevista = er.idRevista ")
                .append("JOIN Etiqueta e ON e.nombre_etiqueta = er.nombre_etiqueta ")
                .append("WHERE e.nombre_etiqueta IN (");

        for (int i = 0; i < etiquetas.size(); i++) {
            query.append("?");
            if (i < etiquetas.size() - 1) {
                query.append(", ");
            }
        }
        query.append(") AND r.aprobada = true");

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < etiquetas.size(); i++) {
                stmt.setString(i + 1, etiquetas.get(i).getNombre());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setIdRevista(rs.getInt("idRevista"));
                    revista.setrevistaPath(rs.getString("revista"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setDescripcion(rs.getString("descripcion"));
                    revista.setVersion(rs.getString("no_version"));
                    revistas.add(revista);
                }
            }

        } catch (SQLException e) {
            try {
                throw new SQLException("Error al buscar revistas por etiquetas", e);
            } catch (SQLException ex) {

            }
        }

        return revistas;
    }
    
    public List<Revista> obtenerRevistasPendientes() {
        List<Revista> revistasAprobadas = new ArrayList<>();
        String query = "SELECT * FROM Revista WHERE aprobada = false AND denegada = false";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setIdRevista(rs.getInt("idRevista"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setDescripcion(rs.getString("descripcion"));
                    revista.setVersion(rs.getString("no_version"));
                    revista.setAprobada(rs.getBoolean("aprobada"));
                    revista.setusuario(rs.getString("nombre_usuario"));
                    revista.setTieneComentarios(rs.getBoolean("comentarios"));
                    revista.setTieneReacciones(rs.getBoolean("reacciones"));
                    revista.setSuscripciones(rs.getBoolean("suscripciones"));
                    revista.setDenegada(rs.getBoolean("denegada"));
                    revistasAprobadas.add(revista);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las revistas aprobadas: " + e.getMessage());
        }

        return revistasAprobadas;
    }
    
    public List<Revista> obtenerRevistasAprobada() {
        List<Revista> revistasAprobadas = new ArrayList<>();
        String query = "SELECT * FROM Revista WHERE aprobada = true AND denegada = false";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setIdRevista(rs.getInt("idRevista"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setDescripcion(rs.getString("descripcion"));
                    revista.setVersion(rs.getString("no_version"));
                    revista.setAprobada(rs.getBoolean("aprobada"));
                    revista.setusuario(rs.getString("nombre_usuario"));
                    revista.setTieneComentarios(rs.getBoolean("comentarios"));
                    revista.setTieneReacciones(rs.getBoolean("reacciones"));
                    revista.setSuscripciones(rs.getBoolean("suscripciones"));
                    revista.setDenegada(rs.getBoolean("denegada"));
                    revistasAprobadas.add(revista);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las revistas aprobadas: " + e.getMessage());
        }

        return revistasAprobadas;
    }
    
    public List<Revista> obtenerRevistasSuscritas(String username) {
        List<Revista> revistasSuscritas = new ArrayList<>();
        String query = "SELECT r.* FROM Revista r "
                + "JOIN Suscripcion s ON r.idRevista = s.idRevista "
                + "WHERE s.nombre_usuario = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Revista revista = new Revista();
                    revista.setIdRevista(rs.getInt("idRevista"));
                    revista.setTitulo(rs.getString("titulo"));
                    revista.setDescripcion(rs.getString("descripcion"));
                    revista.setVersion(rs.getString("no_version"));
                    revista.setAprobada(rs.getBoolean("aprobada"));
                    revista.setusuario(rs.getString("nombre_usuario"));
                    revista.setTieneComentarios(rs.getBoolean("comentarios"));
                    revista.setTieneReacciones(rs.getBoolean("reacciones"));
                    revista.setSuscripciones(rs.getBoolean("suscripciones"));

                    revistasSuscritas.add(revista);
                }
            } catch (SQLException e) {
                System.out.println("Error al consultar las revistas suscritas: " + e.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println("error " + ex);
        }

        return revistasSuscritas;
    }

}

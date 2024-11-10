/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObtenerObjetos;

import Backend.DB.ConexionPool;
import Models.Etiqueta;
import Models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alesso
 */
public class ObGeneral {
    
    private ConexionPool dataSource = ConexionPool.getInstance();
    private Connection connection;
    
    public ObGeneral(){
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }
    }
    
    public Usuario obtenerUsuario(String nombre) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ?";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, nombre);
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                usuario = new Usuario(r.getInt(3), r.getString(1), "");
            }
        } catch (Exception e) {
        }
        return usuario;
    }

    public ArrayList<Etiqueta> obtenerEtiquetas(int opcion, int id) {
        ArrayList<Etiqueta> etiquetas = new ArrayList<>();
        String query = opcion == 1 ? "SELECT * FROM Etiqueta_Revista WHERE idRevista = ?;" : "SELECT * FROM Perfil_Etiquetas WHERE idPerfil = ?;";
        String atributo = opcion == 1 ? "nombre_etiqueta" : "nombre_etiqueta";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setInt(1, id);
            ResultSet r = prepared.executeQuery();
            while (r.next()) {
                etiquetas.add(new Etiqueta(r.getString(atributo)));
            }
        } catch (SQLException e) {
        }
        return etiquetas;
    }
}

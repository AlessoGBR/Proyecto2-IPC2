/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import Models.Etiqueta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alesso
 */
public class EtiquetasDB {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public EtiquetasDB() {

    }

    public List<Etiqueta> buscarEtiquetas() {
        List<Etiqueta> etiquetas = new ArrayList<>();
        String sql = "SELECT * FROM Etiqueta";

        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setNombre(resultSet.getString("nombre_etiqueta"));
                etiquetas.add(etiqueta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return etiquetas;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alesso
 */
public class Conexion {

    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/Proyecto2";
    private static final String USER = "root";
    private static final String PASSWORD = "26359";
    public static Connection connection;

    public Conexion() {

    }

    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error al registrar el driver de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la DB: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

}

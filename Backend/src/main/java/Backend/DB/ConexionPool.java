/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author alesso
 */
public class ConexionPool {

    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/Proyecto2";
    private static final String USER = "root";
    private static final String PASSWORD = "26359";

    public static Connection connection;
    private DataSource datasource;
    private static ConexionPool unicaInstanciaDeDataSource;

    public ConexionPool() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PoolProperties p = new PoolProperties();
            p.setUrl(URL_MYSQL);
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(USER);
            p.setPassword(PASSWORD);
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(1000);
            p.setInitialSize(10);
            p.setMaxWait(1000);
            p.setRemoveAbandonedTimeout(600);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                    + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            datasource = new DataSource(p);
        } catch (ClassNotFoundException e) {
            System.out.println("error: " + e);
        }

    }
    
    public static ConexionPool getInstance(){
        if (unicaInstanciaDeDataSource == null) {
            unicaInstanciaDeDataSource = new ConexionPool();
        }
        
        return unicaInstanciaDeDataSource;
    }

    public DataSource getDataSource() {
        return datasource;
    }

    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

}

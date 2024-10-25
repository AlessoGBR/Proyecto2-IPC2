/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import static Backend.DB.ConexionPool.connection;
import Models.Etiqueta;
import Models.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author alesso
 */
public class CrearUsuario {

    private ConexionPool dataSource = ConexionPool.getInstance();

    private Usuario usuario;
    private String username;
    private int tipo;

    public CrearUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean CrearUser() {
        if (registrarUsuarioYPerfil(usuario)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean usuarioExiste(Connection connection, String nombreUsuario) throws SQLException {
        String sql = "SELECT 1 FROM Usuario WHERE nombre_usuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void insertarUsuario(Connection connection, Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (nombre_usuario, password, idTipoUsuario) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, encriptarPass(usuario.getPassword()));
            ps.setInt(3, convertirTipoUsuarioEnum(usuario.getUserType())); 
            username = usuario.getUsername();
            tipo = convertirTipoUsuarioEnum(usuario.getUserType());
            ps.executeUpdate();
            
        }
        
    }

    public boolean registrarUsuarioYPerfil(Usuario usuario) {
        try (Connection connection = dataSource.getConnection()) { // Obtener conexión del pool
            connection.setAutoCommit(false);
            boolean existe = usuarioExiste(connection, usuario.getUsername());
            if (!existe) {
                insertarUsuario(connection, usuario);
            }
            if (tipo == 4) {
                crearAnunciante(connection,username);
            }

            String sqlPerfil = "INSERT INTO Perfil (fotoPerfil, descripcion, nombre_usuario) VALUES (?, ?, ?)";
            try (PreparedStatement psPerfil = connection.prepareStatement(sqlPerfil)) {
                psPerfil.setString(1, usuario.getFoto());
                psPerfil.setString(2, usuario.getDescripcion());
                psPerfil.setString(3, usuario.getUsername());
                psPerfil.executeUpdate();
            }

            ingresarEtiquetas(connection, obtenerIdPerfil(connection, usuario.getUsername()), usuario.getUsername(), usuario.getEtiquetas());

            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción (por ejemplo, loguear)
            try {
                if (dataSource.getConnection() != null) {
                    dataSource.getConnection().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    private int convertirTipoUsuarioEnum(String tipoUsuario) throws SQLException {
        switch (tipoUsuario) {
            case "Administrador":
                return 1;
            case "Lector":
                return 2;
            case "Editor":
                return 3;
            case "Anunciante":
                return 4;
            default:
                throw new SQLException("Tipo de usuario desconocido.");
        }
    }

    public String encriptarPass(String password) {
        String nuevoPass = "";
        String clave = "ipc2";
        try {
            MessageDigest tipoEncriptacion = MessageDigest.getInstance("MD5");
            byte[] llaveContra = tipoEncriptacion.digest(clave.getBytes("utf-8"));
            byte[] llaveByte = Arrays.copyOf(llaveContra, 24);
            SecretKey llave = new SecretKeySpec(llaveByte, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, llave);
            byte[] textoPlano = password.getBytes("utf-8");
            byte[] buffer = cifrado.doFinal(textoPlano);
            byte[] base64Bytes = Base64.getEncoder().encode(buffer);
            nuevoPass = new String(base64Bytes);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace(); 
        }
        return nuevoPass;
    }

    private int obtenerIdPerfil(Connection connection, String nombreUsuario) throws SQLException {
        String sql = "SELECT idPerfil FROM Perfil WHERE nombre_usuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idPerfil");
                } else {
                    throw new SQLException("No se encontró el perfil para el nombre de usuario proporcionado.");
                }
            }
        }
    }

    private void ingresarEtiquetas(Connection connection, int idPerfil, String nombreUsuario, List<Etiqueta> etiquetas) throws SQLException {
        String sqlInsertRel = "INSERT INTO Perfil_Etiquetas (nombre_etiqueta, idPerfil, nombre_usuario) VALUES (?, ?, ?)";
        try (PreparedStatement psInsertRel = connection.prepareStatement(sqlInsertRel)) {
            for (Etiqueta etiqueta : etiquetas) {
                psInsertRel.setString(1, etiqueta.getNombre());
                psInsertRel.setInt(2, idPerfil);
                psInsertRel.setString(3, nombreUsuario);
                psInsertRel.addBatch();
            }
            psInsertRel.executeBatch();
        }
    }
    
    private void crearAnunciante(Connection connection,String username){
        
        String sql = "INSERT INTO Anunciante (cartera, nombre_usuario) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, 0);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error" + ex);
        }
    }
}

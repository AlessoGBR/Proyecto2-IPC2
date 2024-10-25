/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DB;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author alesso
 */
public class InicioSesionDB {

    private final ConexionPool dataSource = ConexionPool.getInstance();

    public InicioSesionDB() {
    }

    public String desencriptarPass(String password, String clave) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String pass = "";
        try {
            byte[] entrada = Base64.getDecoder().decode(password.getBytes("utf-8"));
            MessageDigest tipoEncriptacion = MessageDigest.getInstance("MD5");
            byte[] claveProcesada = tipoEncriptacion.digest(clave.getBytes("utf-8"));
            byte[] llaveByte = Arrays.copyOf(claveProcesada, 24);
            SecretKey llave = new SecretKeySpec(llaveByte, "DESede");
            Cipher descifrar = Cipher.getInstance("DESede");
            descifrar.init(Cipher.DECRYPT_MODE, llave);
            byte[] textoPlano = descifrar.doFinal(entrada);
            pass = new String(textoPlano, "UTF-8");

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.err.println("Error al desencriptar la contraseña: " + e.getMessage());
        }
        return pass;
    }

    public String verificarUser(String username) {
        String sql = "SELECT password FROM Usuario WHERE nombre_usuario = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar usuario: " + e.getMessage());
        }

        return null;
    }

    public Integer verificarTipoUser(String username) {
        String sql = "SELECT idTipoUsuario FROM Usuario WHERE nombre_usuario = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idTipoUsuario");
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar el tipo de usuario: " + e.getMessage());
        }

        return null;
    }

    public boolean verificarUserExist(String username) throws SQLException {
        
        if (dataSource.getConnection() == null) {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
            return false;
        }

        String sql = "SELECT COUNT(*) FROM Usuario WHERE nombre_usuario = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
        }

        return false;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.InicioSesionDB;
import Backend.Respuesta;
import Models.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author alesso
 */
@Path("/InicioSesion")
public class InicioSesion {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inicioSesion(Usuario usuario) {
        String username = usuario.getUsername();
        String password = usuario.getPassword();

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("El usuario y la contraseña no pueden estar vacíos"))
                    .build();
        }
        InicioSesionDB inicio = new InicioSesionDB();
        try {
            if (!inicio.verificarUserExist(username)) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new Respuesta("Error en el registro: usuario ya existente"))
                        .build();
            }
        } catch (SQLException ex) {
            System.out.println("Error al verificar usuario");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Respuesta("Error en el servidor. Intente más tarde."))
                    .build();
        }

        String encryptedPassword = inicio.verificarUser(username);
        try {
            String decryptedPassword = inicio.desencriptarPass(encryptedPassword, "ipc2");
            if (decryptedPassword.equals(password)) {
                Integer tipoUsuario = inicio.verificarTipoUser(username);
                System.out.println(tipoUsuario);
                return Response.status(Response.Status.OK)
                        .entity(new Respuesta("iniciando", tipoUsuario, username))
                        .build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new Respuesta("Usuario o contraseña incorrectos"))
                        .build();
            }
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Respuesta("Error en el servidor. Intente más tarde."))
                    .build();
        }
    }
}

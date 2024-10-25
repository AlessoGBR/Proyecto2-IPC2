/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.CrearUsuario;
import Backend.Respuesta;
import Models.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alesso
 */
@Path("Registro")
public class RegistroServicio {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuario usuario) {

        if (usuario == null
                || usuario.getUsername() == null || usuario.getUsername().isEmpty()
                || usuario.getPassword() == null || usuario.getPassword().isEmpty()
                || usuario.getUserType() == null || usuario.getUserType().isEmpty()
                || usuario.getEtiquetas() == null || usuario.getEtiquetas().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Error en el registro: datos incompletos"))
                    .build();
        }

        
        boolean registroExitoso = new CrearUsuario(usuario).CrearUser();

        if (registroExitoso) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Respuesta("Registro exitoso"))
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT) 
                    .entity(new Respuesta("Error en el registro: usuario ya existente"))
                    .build();
        }
    }
}

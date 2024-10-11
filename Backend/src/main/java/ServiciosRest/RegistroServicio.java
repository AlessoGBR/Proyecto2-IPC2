/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServiciosRest;

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
        
        
        
        boolean registroExitoso = true; 

        if (registroExitoso) {
            return Response.status(Response.Status.CREATED)
                           .entity("{\"mensaje\":\"Registro exitoso\"}")
                           .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"mensaje\":\"Error en el registro\"}")
                           .build();
        }
    }
}

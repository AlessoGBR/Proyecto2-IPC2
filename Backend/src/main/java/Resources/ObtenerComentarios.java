/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.ObtenerComentario;
import Models.Comentario;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alesso
 */
@Path("Comentarios")
public class ObtenerComentarios {
    
    @GET
    @Path("/ObtenerComentarios/{idRevista}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReaccion(@PathParam("idRevista") int idRevista) {        
        ObtenerComentario comentar = new ObtenerComentario();
        List<Comentario> comentario = comentar.obtenerComentariosRevista(idRevista);
        return Response.ok(comentario).build();

    }
}

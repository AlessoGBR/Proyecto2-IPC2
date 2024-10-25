/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.EtiquetasDB;
import Models.Etiqueta;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alesso
 */
@Path("/Etiquetas")
public class EtiquetaService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtiquetas() {
        try {
            EtiquetasDB busqueda = new EtiquetasDB();
            List<Etiqueta> etiquetas = busqueda.buscarEtiquetas();

            if (etiquetas.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("No se encontraron etiquetas.")
                        .build();
            }
            return Response.ok(etiquetas).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al recuperar etiquetas: " + e.getMessage())
                    .build();
        }

    }
}

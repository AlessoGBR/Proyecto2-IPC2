/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServiciosRest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
@Path("Etiqueta")
public class Etiqueta {

    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtiquetas() {
        List<Etiqueta> etiquetas;

        try {
            TypedQuery<Etiqueta> query = em.createQuery("SELECT e FROM Etiqueta e", Etiqueta.class);
            etiquetas = query.getResultList();
            return Response.ok(etiquetas).build();
        } catch (Exception e) {
            e.printStackTrace(); // Considera usar un logger
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al recuperar etiquetas: " + e.getMessage())
                           .build();
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.ObtenerAnuncios;
import Backend.DB.ObtenerRevistas;
import Backend.Respuesta;
import Models.Anuncio;
import Models.Etiqueta;
import Models.Revista;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author alesso
 */
@Path("ObtenerObjetos")
public class obtenerObjetos {

    @POST
    @Path("/revistasPorEditor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasPorEditor(String usuario) {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.obtenerRevistasDelEditor(usuario);

        if (revistas != null && !revistas.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .entity(revistas)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta("No se encontraron revistas para el usuario: " + usuario))
                    .build();
        }
    }

    @GET
    @Path("/revistasLector")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasLector() {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.obtenerRevistasAprobadas();

        if (!revistas.isEmpty()) {
            return Response.ok(revistas).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @POST
    @Path("/revistasBusqueda")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasBusqueda(List<Etiqueta> etiquetas) {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.buscarRevistasPorEtiquetas(etiquetas);

        if (revistas != null && !revistas.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .entity(revistas)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta("No se encontraron revistas"))
                    .build();
        }
    }

    @GET
    @Path("/revistasPendientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasPendientes() {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.obtenerRevistasPendientes();

        if (!revistas.isEmpty()) {
            return Response.ok(revistas).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @GET
    @Path("/revistasAprobadas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasAprobadas() {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.obtenerRevistasAprobada();

        if (!revistas.isEmpty()) {
            return Response.ok(revistas).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @GET
    @Path("/Anuncios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnuncios(@QueryParam("usuario") String username) {
        System.out.println(username);
        ObtenerAnuncios anuncio = new ObtenerAnuncios();
        int id = anuncio.obtenerIdAnunciantePorNombre(username);
        System.out.println(id);
        List<Anuncio> anuncios = anuncio.obtenerRevistasAprobadas(id);

        if (!anuncios.isEmpty()) {
            return Response.ok(anuncios).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @POST
    @Path("/suscripcionRevistas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasSuscripcion(String username) {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.obtenerRevistasSuscritas(username);

        if (!revistas.isEmpty()) {
            return Response.ok(revistas).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.CrearSuscripcion;
import Backend.DB.ObtenerAnuncios;
import Backend.DB.ObtenerEtiquetas;
import Backend.DB.ObtenerPerfil;
import Backend.DB.ObtenerRevistas;
import Backend.Respuesta;
import Models.Anunciante;
import Models.Anuncio;
import Models.Etiqueta;
import Models.Revista;
import Models.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
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
    @Path("/revistasDenegadas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevistasDenegadas() {

        ObtenerRevistas revistasOB = new ObtenerRevistas();
        List<Revista> revistas = revistasOB.obtenerRevistasDenegadas();

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
        ObtenerAnuncios anuncio = new ObtenerAnuncios();
        int id = anuncio.obtenerIdAnunciantePorNombre(username);
        List<Anuncio> anuncios = anuncio.obtenerAnunciosAprobados(id);

        if (!anuncios.isEmpty()) {
            return Response.ok(anuncios).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @GET
    @Path("/AnunciosLista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnunciosLista() {
        ObtenerAnuncios anuncio = new ObtenerAnuncios();
        List<Anuncio> anuncios = anuncio.obtenerAnuncios();

        if (!anuncios.isEmpty()) {
            return Response.ok(anuncios).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @GET
    @Path("/AnunciosListaAprobados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnunciosAprobados() {
        ObtenerAnuncios anuncio = new ObtenerAnuncios();
        List<Anuncio> anuncios = anuncio.obtenerAnunciosAprobados();

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

    @POST
    @Path("/obtenerPerfil")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPerfil(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("No se proporcionó un nombre de usuario válido"))
                    .build();
        }

        String usuarioExtraido = extraerNombre(username);
        if (usuarioExtraido == null || usuarioExtraido.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("El nombre de usuario no es válido"))
                    .build();
        }

        ObtenerPerfil perfilService = new ObtenerPerfil();
        Usuario user = perfilService.obtenerPerfil(usuarioExtraido);
        if (user.getFoto() != null && user.getDescripcion() != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity(new Respuesta("No hay perfil completo disponible para el usuario especificado"))
                    .build();
        }
    }

    @GET
    @Path("/Revista/{idRevista}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerRevista(@PathParam("idRevista") int idRevista) {
        ObtenerRevistas revistas = new ObtenerRevistas();
        Revista revista = revistas.obtenerRevista(idRevista);
        System.out.println(revista.isTieneAnuncios());
        if (revista != null) {
            return Response.ok(revista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private String extraerNombre(String json) {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            String[] partes = json.split(":");
            if (partes.length == 2) {
                String nombre = partes[1].trim();
                if (nombre.startsWith("\"") && nombre.endsWith("\"")) {
                    return nombre.substring(1, nombre.length() - 1);
                }
            }
        }
        return null; 
    }

    @GET
    @Path("/ObtenerAnunciosCliente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnunciosCliente() {
        ObtenerAnuncios anuncio = new ObtenerAnuncios();
        List<Anuncio> anuncios = anuncio.obtenerAnunciosCliente();

        if (!anuncios.isEmpty()) {
            return Response.ok(anuncios).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }

    @GET
    @Path("/ObtenerSuscripciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSuscripciones(
            @QueryParam("nombre_usuario") String nombreUsuario,
            @QueryParam("idRevista") String idRevista) {

        boolean existeSuscripcion = new CrearSuscripcion().verificarSuscripcion(nombreUsuario, idRevista);
        System.out.println(existeSuscripcion);
        if (existeSuscripcion) {
            return Response.ok(true).build();
        } else {
            return Response.ok(false).build();
        }
    }

    @GET
    @Path("/ObtenerAnunciantes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnunciantes() {
        ObtenerAnuncios anuncio = new ObtenerAnuncios();
        ArrayList<Anunciante> anunciantes = anuncio.obtenerAnunciates();
        boolean existeSuscripcion;
        if (anunciantes == null) {
            existeSuscripcion = false;
        } else {
            existeSuscripcion = true;
        }
        if (existeSuscripcion) {
            return Response.ok(anunciantes).build();
        } else {
            return Response.ok(false).build();
        }
    }

    @POST
    @Path("/ObtenerEtiquetas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEtiqueta(String username) {
        ObtenerEtiquetas etiqueta = new ObtenerEtiquetas();
        List<Etiqueta> Etiquetas = etiqueta.obtenerEtiquetas(username);

        if (!Etiquetas.isEmpty()) {
            return Response.ok(Etiquetas).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No hay revistas aprobadas disponibles.")
                    .build();
        }
    }
}

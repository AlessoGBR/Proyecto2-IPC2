/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.ActualizarPreciosAnuncios;
import Backend.DB.CrearAnuncio;
import Backend.GuardarImagen;
import Backend.IngresoAnuncio;
import Backend.Respuesta;
import Models.Anuncio;
import Models.Cartera;
import Models.PrecioAnuncio;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author alesso
 */
@Path("RegistroAnuncio")
public class RegistroAnuncio {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarAnuncio(
            @FormDataParam("tipo") String tipo,
            @FormDataParam("nombreAnunciante") String nombreAnunciante,
            @FormDataParam("diasDuracion") int diasDuracion,
            @FormDataParam("fechaInicio") String fechaInicio,
            @FormDataParam("texto") String texto,
            @FormDataParam("urlVideo") String urlVideo,
            @FormDataParam("imagen") InputStream fileInputStream,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail) {

        Anuncio anuncioDTO = new Anuncio();
        anuncioDTO.setNombreAnunciante(nombreAnunciante);
        anuncioDTO.setTipo(tipo);
        anuncioDTO.setDiasDuracion(diasDuracion);
        anuncioDTO.setTexto(texto);
        anuncioDTO.setUrlVideo(urlVideo);

        try {
            anuncioDTO.setFechaInicio(Date.valueOf(fechaInicio));
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Formato de fecha no válido"))
                    .build();
        }

        boolean registroExitoso = new IngresoAnuncio(anuncioDTO).registrarAnuncio(fileInputStream, fileDetail);

        return registroExitoso
                ? Response.status(Response.Status.CREATED).entity(new Respuesta("Registro exitoso")).build()
                : Response.status(Response.Status.CONFLICT).entity(new Respuesta("Error en el registro")).build();
    }

    @POST
    @Path("/cartera")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresoCartera(Cartera request) {
        double monto = request.getMonto();
        String usuario = request.getUsuario();

        CrearAnuncio cartera = new CrearAnuncio();
        double montoAcutual = cartera.obtenerCartera(usuario);
        monto = montoAcutual + monto;
        if (cartera.ingresoCartera(usuario, monto)) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Respuesta("Registro exitoso"))
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new Respuesta("Error en el registro"))
                    .build();
        }

    }

    @GET
    @Path("/TotalCartera")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response totalCartera(@QueryParam("usuario") String usuario) {
        CrearAnuncio cartera = new CrearAnuncio();
        double montoAcutual = cartera.obtenerCartera(usuario);
        return Response.status(Response.Status.CREATED)
                .entity(montoAcutual)
                .build();

    }

    @POST
    @Path("/carteraEditor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresoCarteraEditor(Cartera request) {
        int monto = request.getMonto();
        String usuario = request.getUsuario();

        CrearAnuncio cartera = new CrearAnuncio();
        int montoAcutual = cartera.obtenerCarteraEditor(usuario);
        monto = montoAcutual + monto;
        if (cartera.ingresoCarteraEditor(usuario, monto)) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Respuesta("Registro exitoso"))
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new Respuesta("Error en el registro"))
                    .build();
        }

    }

    @GET
    @Path("/TotalCarteraEditor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response totalCarteraEditor(@QueryParam("usuario") String usuario) {
        System.out.println(usuario);
        CrearAnuncio cartera = new CrearAnuncio();
        int montoAcutual = cartera.obtenerCarteraEditor(usuario);
        return Response.status(Response.Status.CREATED)
                .entity(montoAcutual)
                .build();

    }

    @PUT
    @Path("/EditarPrecios")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarPrecios(PrecioAnuncio precios) {
        
        boolean ingreso = new ActualizarPreciosAnuncios().actualizar(precios);
        if (ingreso) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/PreciosAnuncios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPreciosAnuncios() {
        PrecioAnuncio precios = new ActualizarPreciosAnuncios().obtenerPrecios(); // Método que consulta la base de datos
        if (precios != null) {
            return Response.ok(precios).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron precios").build();
        }
    }

}

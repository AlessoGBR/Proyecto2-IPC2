/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.CrearAnuncio;
import Backend.GuardarImagen;
import Backend.Respuesta;
import Models.Anuncio;
import Models.Cartera;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

        try {
            anuncioDTO.setFechaInicio(Date.valueOf(fechaInicio));
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Formato de fecha no válido"))
                    .build();
        }
        anuncioDTO.setTexto(texto);
        anuncioDTO.setUrlVideo(urlVideo);

        Date fechaFin = calcularFechaFin(anuncioDTO.getFechaInicio(), anuncioDTO.getDiasDuracion());

        boolean registroExitoso;
        try {
            CrearAnuncio crearAnuncio = new CrearAnuncio();
            int idAnunciante = crearAnuncio.obtenerIdAnunciantePorNombre(anuncioDTO.getNombreAnunciante());
            double cartera = crearAnuncio.obtenerCartera(nombreAnunciante);
            double pago = 0;
            double total;
            switch (anuncioDTO.getTipo()) {
                case "1":
                    pago = 40 * anuncioDTO.getDiasDuracion();
                    if (pago > cartera || cartera <= 0) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(new Respuesta("No cuentas con fondos suficientes"))
                                .build();
                    }
                    total = cartera - pago;
                    crearAnuncio.ingresoCartera(anuncioDTO.getNombreAnunciante(), total);
                    crearAnuncio.crearAnuncioTexto(anuncioDTO.getTexto(), idAnunciante, anuncioDTO.getFechaInicio(), fechaFin, pago);
                    break;

                case "2":
                    pago = 80 * anuncioDTO.getDiasDuracion();
                    if (pago > cartera || cartera <= 0) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(new Respuesta("No cuentas con fondos suficientes"))
                                .build();
                    }
                    total = cartera - pago;
                    crearAnuncio.ingresoCartera(anuncioDTO.getNombreAnunciante(), total);
                    if (fileInputStream != null && fileDetail != null) {
                        GuardarImagen guardarimg = new GuardarImagen();
                        String imagePath = guardarimg.guardarImagen(fileInputStream, fileDetail.getFileName());
                        anuncioDTO.setPathImagen(imagePath);
                        crearAnuncio.crearAnuncioImagenYTexto(anuncioDTO.getTexto(), anuncioDTO.getPathImagen(), idAnunciante, anuncioDTO.getFechaInicio(), fechaFin, pago);
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(new Respuesta("Imagen requerida para este tipo de anuncio"))
                                .build();
                    }
                    break;

                case "3":
                    pago = 100 * anuncioDTO.getDiasDuracion();
                    if (pago > cartera || cartera <= 0) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(new Respuesta("No cuentas con fondos suficientes"))
                                .build();
                    }
                    total = cartera - pago;
                    crearAnuncio.ingresoCartera(anuncioDTO.getNombreAnunciante(), total);
                    crearAnuncio.crearAnuncioVideo(anuncioDTO.getUrlVideo(), idAnunciante, anuncioDTO.getFechaInicio(), fechaFin, pago);
                    break;

                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new Respuesta("Tipo de anuncio no válido"))
                            .build();
            }
            registroExitoso = true;
        } catch (SQLException e) {
            registroExitoso = false;
            System.err.println("Error de base de datos: " + e.getMessage());
        }

        return registroExitoso
                ? Response.status(Response.Status.CREATED).entity(new Respuesta("Registro exitoso")).build()
                : Response.status(Response.Status.CONFLICT).entity(new Respuesta("Error en el registro")).build();
    }

    private Date calcularFechaFin(Date fechaInicio, int diasDuracion) {
        long milliseconds = fechaInicio.getTime() + (diasDuracion * 24L * 60 * 60 * 1000);
        return new Date(milliseconds);
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

}

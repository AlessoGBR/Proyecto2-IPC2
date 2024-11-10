/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.ActualizarRevista;
import Backend.DB.CrearRevista;
import Backend.DB.CrearSuscripcion;
import Backend.DB.ObtenerAnuncios;
import Backend.DB.ObtenerComentario;
import Backend.GuardarArchivo;
import Backend.Respuesta;
import Models.Comentario;
import Models.Etiqueta;
import Models.Revista;
import Models.Suscripcion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Map;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alesso
 */
@Path("RegistroRevista")
public class RegistroRevista {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarRevista(
            @FormDataParam("revista") String revistaJson,
            @FormDataParam("archivo") InputStream archivoInputStream,
            @FormDataParam("archivo") FormDataContentDisposition archivoMeta) {
        if (revistaJson == null && archivoInputStream == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Error en el registro: datos incompletos"))
                    .build();
        }

        Revista revista = convertirJsonARevista(revistaJson);
        if (revista == null || revista.getTitulo() == null || revista.getFecha() == null
                || revista.getDescripcion() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Error en el registro: datos incompletos"))
                    .build();
        }

        GuardarArchivo guardar = new GuardarArchivo();
        String path = guardar.guardarPDF(archivoInputStream, archivoMeta.getFileName());
        if (path.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Respuesta("Error al guardar el archivo"))
                    .build();
        }
        revista.setrevistaPath(path);
        System.out.println(revista.isTieneAnuncios());
        boolean registroExitoso = new CrearRevista(revista).crearRevista();
        if (registroExitoso) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Respuesta("Registro exitoso"))
                    .build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new Respuesta("Error en el registro: revista ya existente"))
                    .build();
        }
    }

    private Revista convertirJsonARevista(String revistaJson) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(revistaJson))) {
            JsonObject jsonObject = jsonReader.readObject();
            Revista revista = new Revista();

            revista.setTitulo(jsonObject.getString("titulo"));
            revista.setDescripcion(jsonObject.getString("descripcion"));
            revista.setNo_version(jsonObject.getInt("version"));
            revista.setFecha(jsonObject.getString("fecha"));
            revista.setSuscripciones(jsonObject.getBoolean("suscripciones"));
            revista.setTieneComentarios(jsonObject.getBoolean("tieneComentarios"));
            revista.setTieneReacciones(jsonObject.getBoolean("tieneReacciones"));
            revista.setTieneAnuncios(jsonObject.getBoolean("tieneAnuncios"));
            revista.setusuario(jsonObject.getString("usuarioCreador"));

            JsonArray etiquetasArray = jsonObject.getJsonArray("etiquetas");
            List<Etiqueta> etiquetas = new ArrayList<>();
            for (JsonValue value : etiquetasArray) {
                JsonObject etiquetaObject = value.asJsonObject();
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setNombre(etiquetaObject.getString("nombre"));
                etiquetas.add(etiqueta);
            }
            revista.setEtiquetas(etiquetas);

            return revista;
        } catch (Exception e) {
            System.err.println("Error al convertir JSON a Revista: " + e.getMessage());
            return null;
        }
    }

    @PUT
    @Path("/verificarCartera")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarCartera(Map<String, Object> datos) {
        boolean anuncios = (boolean) datos.get("anuncios");
        String usuario = (String) datos.get("usuario");
        System.out.println(usuario);
        int precio = new CrearRevista().verificarCartera(usuario);
        boolean actualizado;
        if (precio < 200) {
            actualizado = false;
        } else {
            CrearRevista saldo = new CrearRevista();
            precio = precio - 200;
            if (precio < 0) {
                actualizado = false;
            } else {
                saldo.ingresoCarteraEditor(usuario, precio);
                actualizado = true;
            }

        }

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarComentarios/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarComentarios(@PathParam("idRevista") int idRevista, Map<String, Object> datos) {
        boolean comentariosActivos = (boolean) datos.get("tieneComentarios");
        boolean actualizado = new ActualizarRevista().actualizarEstadoComentarios(idRevista, comentariosActivos);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarMeGusta/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarMeGusta(@PathParam("idRevista") int idRevista, Map<String, Object> datos) {
        boolean megustaActivos = (boolean) datos.get("tieneMeGusta");
        boolean actualizado = new ActualizarRevista().actualizarEstadoMeGusta(idRevista, megustaActivos);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarSuscripciones/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarSuscripciones(@PathParam("idRevista") int idRevista, Map<String, Object> datos) {
        boolean megustaActivos = (boolean) datos.get("tieneSuscripciones");
        boolean actualizado = new ActualizarRevista().actualizarEstadoSuscripciones(idRevista, megustaActivos);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarAprobada/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarAceptada(@PathParam("idRevista") int idRevista, Revista revista) {

        double precio = revista.getPrecio();
        System.out.println(idRevista);
        System.out.println(precio);
        boolean actualizado = new ActualizarRevista().actualizarAceptacion(idRevista, precio);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarDenegada/{idRevista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarDenegada(@PathParam("idRevista") int idRevista, Revista revista) {
        double precio = revista.getPrecio();
        boolean actualizado = new ActualizarRevista().actualizarDenegada(idRevista, precio);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/RegistrarComentario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registroComentario(Comentario comentario) {
        System.out.println(comentario.getFecha());
        System.out.println(comentario.getIdRevista());
        System.out.println(comentario.getNombreUsuario());
        System.out.println(comentario.getComentario());

        int idComentario = new ObtenerComentario().registrarComentario(comentario);
        comentario.setIdComentario(idComentario);
        System.out.println(comentario.getIdComentario());
        boolean ingresado = new ObtenerComentario().registrarComentarioRevista(comentario);

        if (ingresado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/RegistrarSuscripcion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registroSuscripcion(Suscripcion suscripcion) {

        boolean ingresado = new CrearSuscripcion().ingresoSuscripcion(suscripcion);

        if (ingresado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/AnularSuscripcion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response anularSuscripcion(Suscripcion suscripcion) {

        boolean ingresado = new CrearSuscripcion().anularSuscripcion(suscripcion);

        if (ingresado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarAprobadaAnuncio/{idAnuncio}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarAceptadoAnuncio(@PathParam("idAnuncio") int idAnuncio) {

        boolean actualizado = new ObtenerAnuncios().actualizarAceptacion(idAnuncio);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/actualizarDenegadaAnuncio/{idAnuncio}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarDenegadoAnuncio(@PathParam("idAnuncio") int idAnuncio) {
        boolean actualizado = new ObtenerAnuncios().actualizarDenegada(idAnuncio);

        if (actualizado) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}

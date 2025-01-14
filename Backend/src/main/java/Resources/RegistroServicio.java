/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Backend.DB.CrearUsuario;
import Backend.GuardarImagen;
import Backend.Respuesta;
import Models.Etiqueta;
import Models.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author alesso
 */
@Path("Registro")
public class RegistroServicio {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(
            @FormDataParam("username") String username,
            @FormDataParam("password") String password,
            @FormDataParam("descripcion") String descripcion,
            @FormDataParam("userType") String userType,
            @FormDataParam("etiquetas") List<String> etiquetasJson,
            @FormDataParam("imagen") InputStream fileInputStream,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail) {

        Usuario usuario = new Usuario();
        List<Etiqueta> etiquetas = new ArrayList<>();
        for (String json : etiquetasJson) {
            String nombre = extraerNombre(json);
            if (nombre != null) {
                etiquetas.add(new Etiqueta(nombre));
            }
            System.out.println(etiquetas);
        }

        if (username == null || password == null || userType == null || etiquetasJson == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Error en el registro: datos incompletos"))
                    .build();
        }

        usuario.setEtiquetas(etiquetas);
        if (usuario.getEtiquetas() == null || usuario.getEtiquetas().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Respuesta("Error en el registro: datos incompletos"))
                    .build();
        }

        usuario.setUsername(username);
        usuario.setUserType(userType);
        usuario.setPassword(password);
        usuario.setDescripcion(descripcion);

        if (fileInputStream != null && fileDetail != null) {
            String filePath = new GuardarImagen().guardarImagen(fileInputStream, fileDetail.getFileName());
            System.out.println("Imagen guardada en: " + filePath);
            usuario.setFoto(filePath);
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

    private String extraerNombre(String json) {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            String[] partes = json.split(":");
            if (partes.length == 2) {
                String nombre = partes[1].trim();
                // Remover comillas
                if (nombre.startsWith("\"") && nombre.endsWith("\"")) {
                    return nombre.substring(1, nombre.length() - 1);
                }
            }
        }
        return null;
    }

}

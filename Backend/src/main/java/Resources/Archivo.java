/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author alesso
 */
@Path("SubirArchivo")
public class Archivo {

    private static final String PNG = "image/png";

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(PNG)
    public Response obtenerImagen(
            @QueryParam("opcion") Integer opcion,
            @QueryParam("path") String path,
            @Context HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType(PNG);

        if (opcion == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Parámetro opcionReporte es requerido").build();
        }
        try {
            File imageFile = new File(path); 

            if (!imageFile.exists()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Archivo no encontrado")
                        .build();
            }

            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            return Response.ok(imageBytes).build(); 

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar el archivo")
                    .build();
        }

    }
}

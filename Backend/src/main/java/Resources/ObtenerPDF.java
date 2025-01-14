/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author alesso
 */
@Path("Revista")
public class ObtenerPDF {

    @GET
    @Path("/verRevista")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response verRevista(@QueryParam("path") String path) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(path));
            return Response.ok(fileContent)
                    .type("application/pdf") 
                    .header("Content-Disposition", "inline; filename=\"" + Paths.get(path).getFileName().toString() + "\"")
                    .build();
        } catch (IOException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity("File not found").build();
        }
    }

    @GET
    @Path("/descargar")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response descargar(@QueryParam("path") String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return Response.ok(file)
                    .header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"") 
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

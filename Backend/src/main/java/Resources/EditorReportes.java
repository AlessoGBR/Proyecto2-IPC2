/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Resources;

import Reportes.GeneraraRE;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

/**
 *
 * @author alesso
 */
@Path("EditorReportes")
public class EditorReportes {

    private GeneraraRE generar = new GeneraraRE();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response generarReporte(
            @QueryParam("opcionReporte") Integer opcionReporte,
            @QueryParam("fecha_inicio") String fechaInicio,
            @QueryParam("fecha_final") String fechaFinal,
            @QueryParam("usuario") String usuario,
            @QueryParam("idRevista") Integer idRevista,
            @Context HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");

        try {
            if (opcionReporte == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Parámetro opcionReporte es requerido").build();
            }
            generar.generarReporte(usuario, fechaInicio, fechaFinal, idRevista, opcionReporte, response.getOutputStream());
            return Response.ok().build();

        } catch (IOException | NumberFormatException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity("No se pudo generar el reporte: " + e.getMessage()).build();
        }
    }
}

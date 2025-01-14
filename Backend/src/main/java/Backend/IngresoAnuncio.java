/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Models.Anuncio;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author alesso
 */
public class IngresoAnuncio {

    private final Anuncio anuncioDTO;

    public IngresoAnuncio(Anuncio anuncio) {
        this.anuncioDTO = anuncio;
    }

    public boolean registrarAnuncio(InputStream fileInputStream, FormDataContentDisposition fileDetail) {

        Date fechaFin = calcularFechaFin(anuncioDTO.getFechaInicio(), anuncioDTO.getDiasDuracion());
        try {
            Backend.DB.CrearAnuncio crearAnuncio = new Backend.DB.CrearAnuncio();
            int idAnunciante = crearAnuncio.obtenerIdAnunciantePorNombre(anuncioDTO.getNombreAnunciante());
            double cartera = crearAnuncio.obtenerCartera(anuncioDTO.getNombreAnunciante());
            double pago = 0;
            double total;
            switch (anuncioDTO.getTipo()) {
                case "1" -> {
                    pago = 40 * anuncioDTO.getDiasDuracion();
                    if (pago > cartera || cartera <= 0) {
                        return false;
                    }
                    total = cartera - pago;
                    crearAnuncio.ingresoCartera(anuncioDTO.getNombreAnunciante(), total);
                    crearAnuncio.crearAnuncioTexto(anuncioDTO.getTexto(), idAnunciante, anuncioDTO.getFechaInicio(), fechaFin, pago);
                }

                case "2" -> {
                    pago = 80 * anuncioDTO.getDiasDuracion();
                    if (pago > cartera || cartera <= 0) {
                        return false;
                    }
                    total = cartera - pago;
                    crearAnuncio.ingresoCartera(anuncioDTO.getNombreAnunciante(), total);
                    if (fileInputStream != null && fileDetail != null) {
                        GuardarImagen guardarimg = new GuardarImagen();
                        String imagePath = guardarimg.guardarImagen(fileInputStream, fileDetail.getFileName());
                        anuncioDTO.setPathImagen(imagePath);
                        crearAnuncio.crearAnuncioImagenYTexto(anuncioDTO.getTexto(), anuncioDTO.getPathImagen(), idAnunciante, anuncioDTO.getFechaInicio(), fechaFin, pago);
                    } else {
                        return false;
                    }
                }

                case "3" -> {
                    pago = 100 * anuncioDTO.getDiasDuracion();
                    if (pago > cartera || cartera <= 0) {
                        return false;
                    }
                    total = cartera - pago;
                    crearAnuncio.ingresoCartera(anuncioDTO.getNombreAnunciante(), total);
                    crearAnuncio.crearAnuncioVideo(anuncioDTO.getUrlVideo(), idAnunciante, anuncioDTO.getFechaInicio(), fechaFin, pago);
                }

                default -> {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            return false;

        }

    }

    private Date calcularFechaFin(Date fechaInicio, int diasDuracion) {
        long milliseconds = fechaInicio.getTime() + (diasDuracion * 24L * 60 * 60 * 1000);
        return new Date(milliseconds);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author alesso
 */
public class GuardarImagen {

    private static final String CARPETA_BASE = "imagenes";

    public String guardarImagen(InputStream fileInputStream, String fileName) {
        if (fileInputStream == null || fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Archivo o nombre de archivo no válido.");
        }

        try {
            Path carpetaImagenes = Path.of(System.getProperty("user.dir"), CARPETA_BASE);
            Files.createDirectories(carpetaImagenes); 

            Path filePath = carpetaImagenes.resolve(fileName);

            Files.copy(fileInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString(); 
        } catch (IOException ex) {
            System.err.println("Error al guardar la imagen: " + ex.getMessage());
            return ""; 
        }
    }
}

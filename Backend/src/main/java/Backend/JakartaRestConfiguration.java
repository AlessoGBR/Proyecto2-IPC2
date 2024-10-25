package Backend;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("/resources")
public class JakartaRestConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Registra tus recursos y filtros aquí
        resources.add(Resources.RegistroServicio.class); 
        resources.add(Resources.EtiquetaService.class);
        resources.add(Resources.InicioSesion.class);
        resources.add(Resources.RegistroRevista.class);
        resources.add(Resources.obtenerObjetos.class);
        resources.add(Resources.ObtenerReaccion.class);
        resources.add(Resources.ObtenerComentarios.class);
        resources.add(Resources.RegistroAnuncio.class);
        resources.add(Filters.ConfiguracionCors.class);                   // Filtro CORS
        return resources;
    }
}

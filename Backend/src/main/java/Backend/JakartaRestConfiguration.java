package Backend;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Registra tus recursos y filtros aquí
        resources.add(ServiciosRest.RegistroServicio.class); 
        resources.add(ServiciosRest.Etiqueta.class);
        resources.add(Filters.ConfiguracionCors.class);                   // Filtro CORS
        return resources;
    }
}

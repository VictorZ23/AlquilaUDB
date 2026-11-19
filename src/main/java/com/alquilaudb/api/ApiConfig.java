package com.alquilaudb.api;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath("/api")
public class ApiConfig extends ResourceConfig {

    public ApiConfig() {

        // Recursos REST.
        register(EstadoResource.class);
        register(ViviendaResource.class);

        // Filtro de seguridad administrativa.
        register(SeguridadApiFilter.class);

        // Manejo personalizado de errores.
        register(JsonMalformadoMapper.class);
        register(TipoContenidoMapper.class);

        // Procesamiento JSON con Jackson.
        register(JacksonFeature.class);
    }
}

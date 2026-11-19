package com.alquilaudb.api;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApiConfig extends ResourceConfig {

    public ApiConfig() {

        // Primer endpoint: estado de la API.
        register(EstadoResource.class);

        // Segundo endpoint: consulta de viviendas.
        register(ViviendaResource.class);
    }
}


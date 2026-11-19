
package com.alquilaudb.api;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("estado")
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @GET
    public Map<String, String> comprobarEstado() {

        Map<String, String> respuesta = new LinkedHashMap<>();

        respuesta.put("aplicacion", "AlquilaUDB");
        respuesta.put("fase", "III");
        respuesta.put("estado", "API REST activa");

        return respuesta;
    }
}

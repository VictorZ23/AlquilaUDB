package com.alquilaudb.api;

import com.fasterxml.jackson.core.JsonParseException;
import java.util.Collections;
import javax.annotation.Priority;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class JsonMalformadoMapper
        implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException ex) {

        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(Collections.singletonMap(
                        "error",
                        "El contenido JSON de la solicitud no es valido."
                ))
                .build();
    }
}
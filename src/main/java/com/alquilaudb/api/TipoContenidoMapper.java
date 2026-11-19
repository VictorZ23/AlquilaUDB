package com.alquilaudb.api;

import java.util.Collections;
import javax.annotation.Priority;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class TipoContenidoMapper
        implements ExceptionMapper<NotSupportedException> {

    @Override
    public Response toResponse(NotSupportedException ex) {

        return Response
                .status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
                .type(MediaType.APPLICATION_JSON)
                .entity(Collections.singletonMap(
                        "error",
                        "Tipo de contenido no admitido. Utilice application/json."
                ))
                .build();
    }
}

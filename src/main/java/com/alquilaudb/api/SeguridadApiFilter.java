package com.alquilaudb.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class SeguridadApiFilter
        implements ContainerRequestFilter {

    private static final String VARIABLE_CLAVE =
            "ALQUILAUDB_API_KEY";

    private static final String CABECERA_CLAVE =
            "X-API-Key";

    @Override
    public void filter(
            ContainerRequestContext solicitud)
            throws IOException {

        String metodo = solicitud.getMethod();

        String ruta = solicitud.getUriInfo()
                .getPath(false);

        // =====================================
        // OPERACIONES PUBLICAS
        // =====================================

        // Consultas REST.
        if ("GET".equals(metodo)
                || "HEAD".equals(metodo)
                || "OPTIONS".equals(metodo)) {
            return;
        }

        // Publicacion publica de resenas.
        // La validacion sigue a cargo del recurso.
        if ("POST".equals(metodo)
                && ruta.matches(
                        "viviendas/[^/]+/resenas")) {
            return;
        }

        // =====================================
        // OPERACIONES ADMINISTRATIVAS
        // =====================================

        // Leer la clave desde el entorno
        // del servidor, nunca desde el codigo.
        String claveConfigurada =
                System.getenv(VARIABLE_CLAVE);

        // Si no existe configuracion segura,
        // bloquear las operaciones protegidas.
        if (claveConfigurada == null
                || claveConfigurada.length() < 24) {

            rechazar(
                    solicitud,
                    Response.Status.SERVICE_UNAVAILABLE,
                    "El acceso administrativo no esta configurado."
            );
            return;
        }

        // Leer la clave enviada por el cliente.
        String claveRecibida =
                solicitud.getHeaderString(
                        CABECERA_CLAVE);

        if (claveRecibida == null
                || claveRecibida.isEmpty()) {

            rechazar(
                    solicitud,
                    Response.Status.UNAUTHORIZED,
                    "Se requiere una clave de acceso administrativa."
            );
            return;
        }

        // Comparar las claves sin usar equals().
        boolean autorizada = MessageDigest.isEqual(
                claveConfigurada.getBytes(
                        StandardCharsets.UTF_8),
                claveRecibida.getBytes(
                        StandardCharsets.UTF_8)
        );

        if (!autorizada) {

            rechazar(
                    solicitud,
                    Response.Status.FORBIDDEN,
                    "La clave administrativa no es valida."
            );
        }
    }

    private void rechazar(
            ContainerRequestContext solicitud,
            Response.Status estado,
            String mensaje) {

        solicitud.abortWith(
                Response.status(estado)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(
                                Collections.singletonMap(
                                        "error", mensaje
                                )
                        )
                        .build()
        );
    }
}

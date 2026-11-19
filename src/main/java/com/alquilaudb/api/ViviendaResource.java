package com.alquilaudb.api;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import com.alquilaudb.model.Resena;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.spi.CDI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("viviendas")
@Produces(MediaType.APPLICATION_JSON)
public class ViviendaResource {

    private static final Logger LOGGER =
            Logger.getLogger(ViviendaResource.class.getName());

    // =========================================
    // GET /api/viviendas
    // =========================================

    @GET
    public Response listarViviendas() {

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            List<Vivienda> viviendas = dao.listar();

            List<Map<String, Object>> resultado =
                    new ArrayList<>();

            for (Vivienda vivienda : viviendas) {
                resultado.add(convertirVivienda(vivienda));
            }

            return Response.ok(resultado).build();

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE,
                    "Error al consultar viviendas", ex);

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudieron consultar las viviendas."
            );
        }
    }

    // =========================================
    // GET /api/viviendas/{id}
    // =========================================

    @GET
    @Path("{id}")
    public Response obtenerVivienda(
            @PathParam("id") String idTexto) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un numero entero mayor que cero."
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            Vivienda vivienda = dao.buscarPorId(id);

            if (vivienda == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            return Response.ok(
                    convertirVivienda(vivienda)
            ).build();

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE,
                    "Error al consultar vivienda ID " + id, ex);

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo consultar la vivienda."
            );
        }
    }

    // =========================================
    // GET /api/viviendas/{id}/resenas
    // =========================================

    @GET
    @Path("{id}/resenas")
    public Response listarResenas(
            @PathParam("id") String idTexto) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un numero entero mayor que cero."
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            Vivienda vivienda = dao.buscarPorId(id);

            if (vivienda == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            List<Resena> resenas = dao.listarResenas(id);

            List<Map<String, Object>> resultado =
                    new ArrayList<>();

            for (Resena resena : resenas) {
                resultado.add(convertirResena(resena));
            }

            return Response.ok(resultado).build();

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE,
                    "Error al consultar resenas de vivienda ID "
                    + id, ex);

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudieron consultar las resenas."
            );
        }
    }

    // =========================================
    // POST /api/viviendas/{id}/resenas
    // Registrar una nueva resena
    // =========================================

    @POST
    @Path("{id}/resenas")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarResena(
            @PathParam("id") String idTexto,
            Map<String, Object> solicitud) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un numero entero mayor que cero."
            );
        }

        // Validar que exista un objeto JSON.
        if (solicitud == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "Debe enviar los datos de la resena."
            );
        }

        Object autorValor = solicitud.get("autor");
        Object notaValor = solicitud.get("calificacion");
        Object comentarioValor = solicitud.get("comentario");

        // Comprobar tipos de datos.
        if (!(autorValor instanceof String)
                || !(notaValor instanceof Integer)
                || !(comentarioValor instanceof String)) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "Debe enviar autor, calificacion entera y comentario validos."
            );
        }

        String autor = ((String) autorValor).trim();
        int calificacion = (Integer) notaValor;
        String comentario =
                ((String) comentarioValor).trim();

        // Validar el nombre del autor.
        if (autor.length() < 2 || autor.length() > 100) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El autor debe tener entre 2 y 100 caracteres."
            );
        }

        // Validar la calificacion.
        if (calificacion < 1 || calificacion > 5) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "La calificacion debe estar entre 1 y 5."
            );
        }

        // Validar el comentario.
        if (comentario.length() < 10
                || comentario.length() > 1000) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "El comentario debe tener entre 10 y 1000 caracteres."
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            // Verificar que la vivienda exista.
            Vivienda vivienda = dao.buscarPorId(id);

            if (vivienda == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            // Crear una entidad nueva.
            Resena nuevaResena = new Resena();

            nuevaResena.setAutor(autor);
            nuevaResena.setCalificacion(calificacion);
            nuevaResena.setComentario(comentario);

            // Guardar mediante el DAO existente.
            dao.guardarResena(id, nuevaResena);

            // Informar que el registro se creo.
            return Response
                    .status(Response.Status.CREATED)
                    .entity(convertirResena(nuevaResena))
                    .build();

        } catch (IllegalArgumentException ex) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "Los datos de la resena no cumplen las reglas de validacion."
            );

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE,
                    "Error al registrar resena para vivienda "
                    + id, ex);

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo registrar la resena."
            );
        }
    }

    // =========================================
    // METODOS AUXILIARES
    // =========================================

    private ViviendaJpaDAO obtenerDAO() {

        return CDI.current()
                .select(ViviendaJpaDAO.class)
                .get();
    }

    private Integer validarId(String idTexto) {

        try {
            int id = Integer.parseInt(idTexto);

            if (id > 0) {
                return id;
            }

        } catch (NumberFormatException ex) {
            // Identificador invalido.
        }

        return null;
    }

    private Map<String, Object> convertirVivienda(
            Vivienda vivienda) {

        Map<String, Object> datos =
                new LinkedHashMap<>();

        datos.put("id", vivienda.getId());
        datos.put("titulo", vivienda.getTitulo());
        datos.put("tipo", vivienda.getTipo());
        datos.put("direccion", vivienda.getDireccion());
        datos.put("zona", vivienda.getZona());
        datos.put("precio", vivienda.getPrecio());
        datos.put("habitaciones",
                vivienda.getHabitaciones());
        datos.put("descripcion",
                vivienda.getDescripcion());

        return datos;
    }

    private Map<String, Object> convertirResena(
            Resena resena) {

        Map<String, Object> datos =
                new LinkedHashMap<>();

        datos.put("id", resena.getId());
        datos.put("autor", resena.getAutor());
        datos.put("calificacion",
                resena.getCalificacion());
        datos.put("comentario",
                resena.getComentario());

        if (resena.getFecha() != null) {

            SimpleDateFormat formato =
                    new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss"
                    );

            datos.put(
                    "fecha",
                    formato.format(resena.getFecha())
            );

        } else {
            datos.put("fecha", null);
        }

        return datos;
    }

    private Response error(
            Response.Status estado,
            String mensaje) {

        return Response
                .status(estado)
                .entity(Collections.singletonMap(
                        "error", mensaje
                ))
                .build();
    }
}
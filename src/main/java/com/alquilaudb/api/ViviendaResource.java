package com.alquilaudb.api;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import com.alquilaudb.model.Resena;

import java.math.BigDecimal;
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
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("viviendas")
@Produces(MediaType.APPLICATION_JSON)
public class ViviendaResource {

    private static final Logger LOGGER =
            Logger.getLogger(ViviendaResource.class.getName());

    // ==========================================
    // GET /api/viviendas
    // ==========================================

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

            LOGGER.log(
                    Level.SEVERE,
                    "Error al consultar viviendas",
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudieron consultar las viviendas."
            );
        }
    }

    // ==========================================
    // GET /api/viviendas/{id}
    // ==========================================

    @GET
    @Path("{id}")
    public Response obtenerVivienda(
            @PathParam("id") String idTexto) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un entero mayor que cero."
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

            LOGGER.log(
                    Level.SEVERE,
                    "Error al consultar vivienda " + id,
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo consultar la vivienda."
            );
        }
    }

    // ==========================================
    // POST /api/viviendas
    // ==========================================

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarVivienda(
            Map<String, Object> solicitud,
            @Context UriInfo uriInfo) {

        if (solicitud == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "Debe enviar los datos de la vivienda."
            );
        }

        final Vivienda nueva;

        try {
            nueva = leerDatosVivienda(
                    solicitud, false
            );

        } catch (IllegalArgumentException ex) {
            return error(
                    Response.Status.BAD_REQUEST,
                    ex.getMessage()
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            dao.guardar(nueva);

            java.net.URI ubicacion = uriInfo
                    .getAbsolutePathBuilder()
                    .path(String.valueOf(nueva.getId()))
                    .build();

            return Response
                    .created(ubicacion)
                    .entity(convertirVivienda(nueva))
                    .build();

        } catch (Exception ex) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al registrar vivienda",
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo registrar la vivienda."
            );
        }
    }

    // ==========================================
    // PUT /api/viviendas/{id}
    // ==========================================

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarVivienda(
            @PathParam("id") String idTexto,
            Map<String, Object> solicitud) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un entero mayor que cero."
            );
        }

        if (solicitud == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "Debe enviar los datos de la vivienda."
            );
        }

        if (solicitud.containsKey("id")) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El ID debe indicarse en la URL, no en el JSON."
            );
        }

        final Vivienda datosNuevos;

        try {
            datosNuevos = leerDatosVivienda(
                    solicitud, true
            );

        } catch (IllegalArgumentException ex) {
            return error(
                    Response.Status.BAD_REQUEST,
                    ex.getMessage()
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            Vivienda existente = dao.buscarPorId(id);

            if (existente == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            copiarDatos(datosNuevos, existente);

            existente.setDisponible(
                    (Boolean) solicitud.get("disponible")
            );

            dao.actualizar(existente);

            Vivienda actualizada = dao.buscarPorId(id);

            return Response.ok(
                    convertirVivienda(actualizada)
            ).build();

        } catch (Exception ex) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al actualizar vivienda " + id,
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo actualizar la vivienda."
            );
        }
    }

    // ==========================================
    // DELETE /api/viviendas/{id}
    // ==========================================

    @DELETE
    @Path("{id}")
    public Response eliminarVivienda(
            @PathParam("id") String idTexto) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un entero mayor que cero."
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            // Comprobar que exista la vivienda.
            Vivienda vivienda = dao.buscarPorId(id);

            if (vivienda == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            // El DAO comprueba si existen
            // resenas y protege la integridad.
            dao.eliminar(id);

            // Eliminacion correcta.
            // HTTP 204 no devuelve cuerpo.
            return Response.noContent().build();

        } catch (IllegalStateException ex) {

            LOGGER.log(
                    Level.INFO,
                    "Eliminacion rechazada por regla de negocio",
                    ex
            );

            return error(
                    Response.Status.CONFLICT,
                    "No se puede eliminar esta vivienda porque tiene resenas asociadas."
            );

        } catch (Exception ex) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al eliminar vivienda " + id,
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo eliminar la vivienda."
            );
        }
    }

    // ==========================================
    // GET /api/viviendas/{id}/resenas
    // ==========================================

    @GET
    @Path("{id}/resenas")
    public Response listarResenas(
            @PathParam("id") String idTexto) {

        Integer id = validarId(idTexto);

        if (id == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "El identificador debe ser un entero mayor que cero."
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            if (dao.buscarPorId(id) == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            List<Resena> resenas =
                    dao.listarResenas(id);

            List<Map<String, Object>> resultado =
                    new ArrayList<>();

            for (Resena resena : resenas) {
                resultado.add(convertirResena(resena));
            }

            return Response.ok(resultado).build();

        } catch (Exception ex) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al consultar resenas",
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudieron consultar las resenas."
            );
        }
    }

    // ==========================================
    // POST /api/viviendas/{id}/resenas
    // ==========================================

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
                    "El identificador debe ser un entero mayor que cero."
            );
        }

        if (solicitud == null) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "Debe enviar los datos de la resena."
            );
        }

        Object autorValor = solicitud.get("autor");
        Object notaValor = solicitud.get("calificacion");
        Object comentarioValor =
                solicitud.get("comentario");

        if (!(autorValor instanceof String)
                || !(notaValor instanceof Number)
                || !(comentarioValor instanceof String)) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "Debe enviar autor, calificacion y comentario validos."
            );
        }

        String autor = ((String) autorValor).trim();

        String comentario =
                ((String) comentarioValor).trim();

        int calificacion;

        try {
            calificacion = new BigDecimal(
                    notaValor.toString()
            ).intValueExact();

        } catch (ArithmeticException
                | NumberFormatException ex) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "La calificacion debe ser un numero entero."
            );
        }

        if (autor.length() < 2
                || autor.length() > 100) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "El autor debe tener entre 2 y 100 caracteres."
            );
        }

        if (calificacion < 1 || calificacion > 5) {
            return error(
                    Response.Status.BAD_REQUEST,
                    "La calificacion debe estar entre 1 y 5."
            );
        }

        if (comentario.length() < 10
                || comentario.length() > 1000) {

            return error(
                    Response.Status.BAD_REQUEST,
                    "El comentario debe tener entre 10 y 1000 caracteres."
            );
        }

        try {
            ViviendaJpaDAO dao = obtenerDAO();

            if (dao.buscarPorId(id) == null) {
                return error(
                        Response.Status.NOT_FOUND,
                        "No existe una vivienda con el ID indicado."
                );
            }

            Resena nuevaResena = new Resena();

            nuevaResena.setAutor(autor);
            nuevaResena.setCalificacion(calificacion);
            nuevaResena.setComentario(comentario);

            dao.guardarResena(id, nuevaResena);

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

            LOGGER.log(
                    Level.SEVERE,
                    "Error al registrar resena",
                    ex
            );

            return error(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "No se pudo registrar la resena."
            );
        }
    }

    // ==========================================
    // VALIDACIONES DE VIVIENDA
    // ==========================================

    private Vivienda leerDatosVivienda(
            Map<String, Object> solicitud,
            boolean exigirDisponibilidad) {

        String titulo = obtenerTexto(
                solicitud, "titulo", 3, 100);

        String tipo = obtenerTexto(
                solicitud, "tipo", 2, 30);

        String direccion = obtenerTexto(
                solicitud, "direccion", 5, 150);

        String zona = obtenerTexto(
                solicitud, "zona", 2, 100);

        double precio = obtenerPrecio(solicitud);

        int habitaciones =
                obtenerHabitaciones(solicitud);

        String descripcion = "";

        if (solicitud.containsKey("descripcion")) {

            Object valor = solicitud.get("descripcion");

            if (valor != null
                    && !(valor instanceof String)) {

                throw new IllegalArgumentException(
                        "La descripcion debe ser texto."
                );
            }

            if (valor != null) {
                descripcion = ((String) valor).trim();
            }

            if (descripcion.length() > 3000) {
                throw new IllegalArgumentException(
                        "La descripcion no debe superar 3000 caracteres."
                );
            }
        }

        boolean disponible = true;

        if (solicitud.containsKey("disponible")) {

            Object valor = solicitud.get("disponible");

            if (!(valor instanceof Boolean)) {
                throw new IllegalArgumentException(
                        "La disponibilidad debe ser true o false."
                );
            }

            disponible = (Boolean) valor;

        } else if (exigirDisponibilidad) {

            throw new IllegalArgumentException(
                    "Debe indicar la disponibilidad de la vivienda."
            );
        }

        Vivienda vivienda = new Vivienda();

        vivienda.setTitulo(titulo);
        vivienda.setTipo(tipo);
        vivienda.setDireccion(direccion);
        vivienda.setZona(zona);
        vivienda.setPrecio(precio);
        vivienda.setHabitaciones(habitaciones);
        vivienda.setDescripcion(descripcion);
        vivienda.setDisponible(disponible);

        return vivienda;
    }

    private void copiarDatos(
            Vivienda origen,
            Vivienda destino) {

        destino.setTitulo(origen.getTitulo());
        destino.setTipo(origen.getTipo());
        destino.setDireccion(origen.getDireccion());
        destino.setZona(origen.getZona());
        destino.setPrecio(origen.getPrecio());
        destino.setHabitaciones(origen.getHabitaciones());
        destino.setDescripcion(origen.getDescripcion());
    }

    private String obtenerTexto(
            Map<String, Object> solicitud,
            String campo,
            int minimo,
            int maximo) {

        Object valor = solicitud.get(campo);

        if (!(valor instanceof String)) {
            throw new IllegalArgumentException(
                    "El campo " + campo
                    + " debe contener texto."
            );
        }

        String texto = ((String) valor).trim();

        if (texto.length() < minimo
                || texto.length() > maximo) {

            throw new IllegalArgumentException(
                    "El campo " + campo
                    + " debe tener entre "
                    + minimo + " y "
                    + maximo + " caracteres."
            );
        }

        return texto;
    }

    private double obtenerPrecio(
            Map<String, Object> solicitud) {

        Object valor = solicitud.get("precio");

        if (!(valor instanceof Number)) {
            throw new IllegalArgumentException(
                    "El precio debe ser numerico."
            );
        }

        BigDecimal precio;

        try {
            precio = new BigDecimal(valor.toString());

        } catch (NumberFormatException ex) {

            throw new IllegalArgumentException(
                    "El precio no es valido."
            );
        }

        if (precio.compareTo(BigDecimal.ZERO) <= 0
                || precio.compareTo(
                        new BigDecimal("99999999.99")) > 0
                || precio.scale() > 2) {

            throw new IllegalArgumentException(
                    "El precio debe ser positivo, "
                    + "tener como maximo dos decimales "
                    + "y no superar 99999999.99."
            );
        }

        return precio.doubleValue();
    }

    private int obtenerHabitaciones(
            Map<String, Object> solicitud) {

        Object valor = solicitud.get("habitaciones");

        if (!(valor instanceof Number)) {
            throw new IllegalArgumentException(
                    "Las habitaciones deben ser un numero."
            );
        }

        int habitaciones;

        try {
            habitaciones = new BigDecimal(
                    valor.toString()
            ).intValueExact();

        } catch (ArithmeticException
                | NumberFormatException ex) {

            throw new IllegalArgumentException(
                    "Las habitaciones deben ser un entero."
            );
        }

        if (habitaciones < 1 || habitaciones > 100) {

            throw new IllegalArgumentException(
                    "Las habitaciones deben estar entre 1 y 100."
            );
        }

        return habitaciones;
    }

    // ==========================================
    // METODOS AUXILIARES
    // ==========================================

    private ViviendaJpaDAO obtenerDAO() {

        return CDI.current()
                .select(ViviendaJpaDAO.class)
                .get();
    }

    private Integer validarId(String texto) {

        try {
            int id = Integer.parseInt(texto);
            return id > 0 ? id : null;

        } catch (NumberFormatException ex) {
            return null;
        }
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
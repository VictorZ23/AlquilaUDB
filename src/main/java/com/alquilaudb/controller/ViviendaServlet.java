/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alquilaudb.controller;

import com.alquilaudb.dao.ViviendaDAO;
import com.alquilaudb.model.Vivienda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViviendaServlet", urlPatterns = {"/vivienda"})
public class ViviendaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ViviendaDAO dao = new ViviendaDAO();
        String accion = request.getParameter("accion");

        // DETALLE
        if ("detalle".equals(accion)) {

            try {
                int id = Integer.parseInt(request.getParameter("id"));

                Vivienda vivienda = dao.buscarPorId(id);

                if (vivienda != null) {
                    request.setAttribute("vivienda", vivienda);

                    request.getRequestDispatcher("/detalle-vivienda.jsp")
                            .forward(request, response);

                    return;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // EDITAR
        if ("editar".equals(accion)) {

            try {
                int id = Integer.parseInt(request.getParameter("id"));

                Vivienda vivienda = dao.buscarPorId(id);

                if (vivienda != null) {
                    request.setAttribute("vivienda", vivienda);

                    request.getRequestDispatcher("/formulario-vivienda.jsp")
                            .forward(request, response);

                    return;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // ELIMINAR
        if ("eliminar".equals(accion)) {

            try {
                int id = Integer.parseInt(request.getParameter("id"));

                boolean eliminado = dao.eliminar(id);

                if (eliminado) {
                    response.sendRedirect(
                            request.getContextPath()
                            + "/vivienda?mensaje=eliminada"
                    );
                } else {
                    response.sendRedirect(
                            request.getContextPath()
                            + "/vivienda?mensaje=error"
                    );
                }

                return;

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // LISTAR
        List<Vivienda> viviendas = dao.listar();

        request.setAttribute("viviendas", viviendas);

        request.getRequestDispatcher("/viviendas.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idTexto = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String tipo = request.getParameter("tipo");
        String direccion = request.getParameter("direccion");
        String zona = request.getParameter("zona");
        String precioTexto = request.getParameter("precio");
        String habitacionesTexto = request.getParameter("habitaciones");
        String descripcion = request.getParameter("descripcion");

        boolean disponible =
                request.getParameter("disponible") != null;

        List<String> errores = new ArrayList<>();

        // VALIDACIÓN DE CAMPOS DE TEXTO
        if (titulo == null || titulo.trim().isEmpty()) {
            errores.add("El título es obligatorio.");
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            errores.add("La dirección es obligatoria.");
        }

        if (zona == null || zona.trim().isEmpty()) {
            errores.add("La zona es obligatoria.");
        }

        // VALIDACIÓN DEL TIPO
        if (tipo == null
                || (!tipo.equals("Casa")
                && !tipo.equals("Apartamento")
                && !tipo.equals("Habitación"))) {

            errores.add("Debe seleccionar un tipo de vivienda válido.");
        }

        double precio = 0;

        try {
            precio = Double.parseDouble(precioTexto);

            if (precio <= 0) {
                errores.add("El precio debe ser mayor que cero.");
            }

        } catch (NumberFormatException e) {
            errores.add("El precio debe ser un número válido.");
        }

        int habitaciones = 0;

        try {
            habitaciones = Integer.parseInt(habitacionesTexto);

            if (habitaciones < 1) {
                errores.add("La vivienda debe tener al menos una habitación.");
            }

        } catch (NumberFormatException e) {
            errores.add("La cantidad de habitaciones debe ser un número válido.");
        }

        // CREAMOS EL OBJETO PARA CONSERVAR LOS DATOS DEL FORMULARIO
        Vivienda vivienda = new Vivienda();

        vivienda.setTitulo(titulo);
        vivienda.setTipo(tipo);
        vivienda.setDireccion(direccion);
        vivienda.setZona(zona);
        vivienda.setPrecio(precio);
        vivienda.setHabitaciones(habitaciones);
        vivienda.setDescripcion(descripcion);
        vivienda.setDisponible(disponible);

        if (idTexto != null && !idTexto.trim().isEmpty()) {

            try {
                vivienda.setId(Integer.parseInt(idTexto));
            } catch (NumberFormatException e) {
                errores.add("El identificador de la vivienda no es válido.");
            }
        }

        // SI EXISTEN ERRORES, VOLVEMOS AL FORMULARIO
        if (!errores.isEmpty()) {

            request.setAttribute("errores", errores);
            request.setAttribute("vivienda", vivienda);

            request.getRequestDispatcher("/formulario-vivienda.jsp")
                    .forward(request, response);

            return;
        }

        ViviendaDAO dao = new ViviendaDAO();

        // ACTUALIZAR
        if (idTexto != null && !idTexto.trim().isEmpty()) {

            boolean actualizado = dao.actualizar(vivienda);

            if (actualizado) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/vivienda?mensaje=actualizada"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath()
                        + "/vivienda?mensaje=error"
                );
            }

        // INSERTAR
        } else {

            boolean insertado = dao.insertar(vivienda);

            if (insertado) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/vivienda?mensaje=creada"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath()
                        + "/vivienda?mensaje=error"
                );
            }
        }
    }
}
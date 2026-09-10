/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alquilaudb.dao;

import com.alquilaudb.model.Vivienda;
import java.util.List;

public class PruebaViviendaDAO {

    public static void main(String[] args) {

        ViviendaDAO dao = new ViviendaDAO();

        // =========================================================
        // 1. INSERTAR UNA VIVIENDA
        // =========================================================

        Vivienda nueva = new Vivienda();

        nueva.setTitulo("Habitación económica para estudiante");
        nueva.setTipo("Habitación");
        nueva.setDireccion("Colonia Escalón");
        nueva.setZona("San Salvador");
        nueva.setPrecio(180.00);
        nueva.setHabitaciones(1);
        nueva.setDescripcion("Habitación individual en vivienda compartida.");
        nueva.setDisponible(true);

        boolean insertado = dao.insertar(nueva);

        System.out.println("¿Se insertó la vivienda? " + insertado);


        // =========================================================
        // 2. LISTAR TODAS LAS VIVIENDAS
        // =========================================================

        List<Vivienda> viviendas = dao.listar();

        System.out.println("\n===== LISTADO DE VIVIENDAS =====");

        for (Vivienda vivienda : viviendas) {

            System.out.println("ID: " + vivienda.getId());
            System.out.println("Título: " + vivienda.getTitulo());
            System.out.println("Tipo: " + vivienda.getTipo());
            System.out.println("Dirección: " + vivienda.getDireccion());
            System.out.println("Zona: " + vivienda.getZona());
            System.out.println("Precio: $" + vivienda.getPrecio());
            System.out.println("Habitaciones: " + vivienda.getHabitaciones());
            System.out.println("Descripción: " + vivienda.getDescripcion());
            System.out.println("Disponible: " + vivienda.isDisponible());

            System.out.println("------------------------------");
        }


        // =========================================================
        // 3. ACTUALIZAR UNA VIVIENDA
        // =========================================================

        Vivienda viviendaActualizar = new Vivienda();

        // IMPORTANTE:
        // Este ID debe existir en la base de datos.
        viviendaActualizar.setId(1);

        viviendaActualizar.setTitulo("Apartamento actualizado");
        viviendaActualizar.setTipo("Apartamento");
        viviendaActualizar.setDireccion("Colonia Centro");
        viviendaActualizar.setZona("San Salvador");
        viviendaActualizar.setPrecio(300.00);
        viviendaActualizar.setHabitaciones(2);
        viviendaActualizar.setDescripcion(
                "Apartamento actualizado desde Java."
        );
        viviendaActualizar.setDisponible(true);

        boolean actualizado = dao.actualizar(viviendaActualizar);

        System.out.println("\n¿Se actualizó la vivienda? " + actualizado);


        // =========================================================
        // 4. ELIMINAR UNA VIVIENDA
        // =========================================================

        // IMPORTANTE:
        // Utiliza un ID que realmente exista y que quieras eliminar.
        // Por ejemplo, la vivienda 3 que creamos durante las pruebas.

        boolean eliminado = dao.eliminar(3);

        System.out.println("¿Se eliminó la vivienda? " + eliminado);


        // =========================================================
        // 5. LISTAR NUEVAMENTE
        // =========================================================

        viviendas = dao.listar();

        System.out.println("\n===== LISTADO FINAL =====");

        for (Vivienda vivienda : viviendas) {

            System.out.println(
                    vivienda.getId()
                    + " - "
                    + vivienda.getTitulo()
                    + " - $"
                    + vivienda.getPrecio()
            );
        }
    }
}
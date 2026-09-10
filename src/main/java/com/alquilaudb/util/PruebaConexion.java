/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alquilaudb.util;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {
    
    public static void main(String[] args) {

        try {
            Connection conexion = Conexion.obtenerConexion();

            System.out.println("Conexión exitosa con la base de datos.");

            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
    }
    
}

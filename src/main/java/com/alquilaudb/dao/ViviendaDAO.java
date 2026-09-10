package com.alquilaudb.dao;

import com.alquilaudb.model.Vivienda;
import com.alquilaudb.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViviendaDAO {

    // =========================================================
    // LISTAR TODAS LAS VIVIENDAS
    // =========================================================
    public List<Vivienda> listar() {

        List<Vivienda> viviendas = new ArrayList<>();

        String sql = "SELECT * FROM vivienda";

        try (
            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Vivienda vivienda = new Vivienda();

                vivienda.setId(resultado.getInt("id"));
                vivienda.setTitulo(resultado.getString("titulo"));
                vivienda.setTipo(resultado.getString("tipo"));
                vivienda.setDireccion(resultado.getString("direccion"));
                vivienda.setZona(resultado.getString("zona"));
                vivienda.setPrecio(resultado.getDouble("precio"));
                vivienda.setHabitaciones(resultado.getInt("habitaciones"));
                vivienda.setDescripcion(resultado.getString("descripcion"));
                vivienda.setDisponible(resultado.getBoolean("disponible"));

                viviendas.add(vivienda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viviendas;
    }


    // =========================================================
    // BUSCAR UNA VIVIENDA POR ID
    // =========================================================
    public Vivienda buscarPorId(int id) {

        String sql = "SELECT * FROM vivienda WHERE id = ?";

        try (
            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {

                    Vivienda vivienda = new Vivienda();

                    vivienda.setId(resultado.getInt("id"));
                    vivienda.setTitulo(resultado.getString("titulo"));
                    vivienda.setTipo(resultado.getString("tipo"));
                    vivienda.setDireccion(resultado.getString("direccion"));
                    vivienda.setZona(resultado.getString("zona"));
                    vivienda.setPrecio(resultado.getDouble("precio"));
                    vivienda.setHabitaciones(resultado.getInt("habitaciones"));
                    vivienda.setDescripcion(resultado.getString("descripcion"));
                    vivienda.setDisponible(resultado.getBoolean("disponible"));

                    return vivienda;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // INSERTAR
    // =========================================================
    public boolean insertar(Vivienda vivienda) {

        String sql = "INSERT INTO vivienda "
                + "(titulo, tipo, direccion, zona, precio, habitaciones, descripcion, disponible) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, vivienda.getTitulo());
            sentencia.setString(2, vivienda.getTipo());
            sentencia.setString(3, vivienda.getDireccion());
            sentencia.setString(4, vivienda.getZona());
            sentencia.setDouble(5, vivienda.getPrecio());
            sentencia.setInt(6, vivienda.getHabitaciones());
            sentencia.setString(7, vivienda.getDescripcion());
            sentencia.setBoolean(8, vivienda.isDisponible());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // ACTUALIZAR
    // =========================================================
    public boolean actualizar(Vivienda vivienda) {

        String sql = "UPDATE vivienda SET "
                + "titulo = ?, "
                + "tipo = ?, "
                + "direccion = ?, "
                + "zona = ?, "
                + "precio = ?, "
                + "habitaciones = ?, "
                + "descripcion = ?, "
                + "disponible = ? "
                + "WHERE id = ?";

        try (
            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, vivienda.getTitulo());
            sentencia.setString(2, vivienda.getTipo());
            sentencia.setString(3, vivienda.getDireccion());
            sentencia.setString(4, vivienda.getZona());
            sentencia.setDouble(5, vivienda.getPrecio());
            sentencia.setInt(6, vivienda.getHabitaciones());
            sentencia.setString(7, vivienda.getDescripcion());
            sentencia.setBoolean(8, vivienda.isDisponible());
            sentencia.setInt(9, vivienda.getId());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // ELIMINAR
    // =========================================================
    public boolean eliminar(int id) {

        String sql = "DELETE FROM vivienda WHERE id = ?";

        try (
            Connection conexion = Conexion.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, id);

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
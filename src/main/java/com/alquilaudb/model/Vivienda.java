/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alquilaudb.model;

/**
 *
 * @author Admin
 */
public class Vivienda {
    
    private int id;
    private String titulo;
    private String tipo;
    private String direccion;
    private String zona;
    private double precio;
    private int habitaciones;
    private String descripcion;
    private boolean disponible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Vivienda() {
        
    }
    
    public Vivienda(int id, String titulo, String tipo, String direccion,
        String zona, double precio, int habitaciones,
        String descripcion, boolean disponible) {
    
    this.id = id;
    this.titulo = titulo;
    this.tipo = tipo;
    this.direccion = direccion;
    this.zona = zona;
    this.precio = precio;
    this.habitaciones = habitaciones;
    this.descripcion = descripcion;
    this.disponible = disponible;
}
}




package com.alquilaudb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vivienda")
public class Vivienda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "zona")
    private String zona;

    @Column(name = "precio")
    private double precio;

    @Column(name = "habitaciones")
    private int habitaciones;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "disponible")
    private boolean disponible;

    @OneToMany(
            mappedBy = "vivienda",
            fetch = FetchType.LAZY
    )
    private List<Resena> resenas = new ArrayList<>();

    public Vivienda() {
    }

    public Vivienda(int id, String titulo, String tipo,
            String direccion, String zona, double precio,
            int habitaciones, String descripcion,
            boolean disponible) {

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

    public List<Resena> getResenas() {
        return resenas;
    }

    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }
}

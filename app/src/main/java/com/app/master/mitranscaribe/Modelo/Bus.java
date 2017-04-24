package com.app.master.mitranscaribe.Modelo;

import android.util.Log;

/**
 * Created by Rafael p on 21/4/2017.
 */

public class Bus {
    private String nombre,estado;
    private double latitud,longitud;

    public Bus() {
    }

    public Bus(String nombre, double latitud, double longitud,String estado) {
        this.nombre = nombre;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
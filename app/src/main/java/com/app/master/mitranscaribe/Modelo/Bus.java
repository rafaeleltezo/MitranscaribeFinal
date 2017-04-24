package com.app.master.mitranscaribe.Modelo;

import android.util.Log;

/**
 * Created by Rafael p on 21/4/2017.
 */

public class Bus {
    private String nombre,estado,latitud,longitud;

    public Bus() {
    }

    public Bus(String nombre, String estado, String latitud, String longitud) {
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
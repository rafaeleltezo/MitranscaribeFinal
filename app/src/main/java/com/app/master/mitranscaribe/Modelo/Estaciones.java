package com.app.master.mitranscaribe.Modelo;

/**
 * Created by Rafael p on 15/4/2017.
 */

public class Estaciones {
    String id;
    double latitud;
    double Longitud;
    String tituloEstacion;

    public Estaciones(String id, double latitud, double longitud, String nombre) {
        this.id = id;
        this.latitud = latitud;
        Longitud = longitud;
        this.tituloEstacion = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public String getNombre() {
        return tituloEstacion;
    }

    public void setNombre(String nombre) {
        this.tituloEstacion = nombre;
    }
}

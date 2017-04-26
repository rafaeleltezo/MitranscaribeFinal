package com.app.master.mitranscaribe.Modelo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Rafael p on 15/4/2017.
 */

public class Paradero {
    double latitud;
    double longitud;
    String tituloEstacion;

    public Paradero(){
        latitud=0;
        longitud=0;
        tituloEstacion="";

    }

    public Paradero(double latitud, double longitud, String nombre) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.tituloEstacion = nombre;
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
        longitud = longitud;
    }

    public String getNombre() {
        return tituloEstacion;
    }

    public void setNombre(String nombre) {
        this.tituloEstacion = nombre;
    }
}

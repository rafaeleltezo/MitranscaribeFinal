package com.app.master.mitranscaribe.Modelo;

/**
 * Created by Rafael p on 19/6/2017.
 */

public class Coordenadas {
    private double latitud,longitud,distancia;

    public Coordenadas() {
    }

    public Coordenadas(double latitud, double longitud, double distancia) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.distancia = distancia;
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

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}

package com.app.master.mitranscaribe.Modelo;

/**
 * Created by Rafael p on 21/4/2017.
 */

public class Bus {
    private String id,nombre,placa;
    double longitud,latitud;

    public Bus(){
        id="";
        nombre="";
        placa="";
        longitud=0;
        latitud=0;
    }
    public Bus(String id, String nombre, String placa, double longitud, double latitud) {
        this.id = id;
        this.nombre = nombre;
        this.placa = placa;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}

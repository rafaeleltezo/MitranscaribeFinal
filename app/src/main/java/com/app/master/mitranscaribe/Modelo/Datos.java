package com.app.master.mitranscaribe.Modelo;

import java.util.ArrayList;

/**
 * Created by Rafael p on 15/4/2017.
 */

public class Datos {

    public ArrayList<Estaciones> getPosicionEstaciones(){
        ArrayList<Estaciones>estaciones=new ArrayList<>();
        estaciones.add(new Estaciones(10,3842245,-754583447,"coveñas"));
        estaciones.add(new Estaciones(10,3842245,-754583457,"coveñas2"));
        estaciones.add(new Estaciones(10,3842245,-754583467,"coveñas3"));
        estaciones.add(new Estaciones(10,3842245,-754583477,"coveñas4"));
        return estaciones;
    }
}

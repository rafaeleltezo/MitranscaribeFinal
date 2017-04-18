package com.app.master.mitranscaribe.Modelo;

import java.util.ArrayList;

/**
 * Created by Rafael p on 15/4/2017.
 */

public class Datos {

    public ArrayList<Estaciones> getPosicionEstaciones(){
        ArrayList<Estaciones>estaciones=new ArrayList<>();
        estaciones.add(new Estaciones(111,9.41936,-75.65075,"coveñas"));
        estaciones.add(new Estaciones(112,9.41924,-75.65075,"coveñas2"));
        estaciones.add(new Estaciones(113,9.41965,-75.65075,"coveñas3"));
        estaciones.add(new Estaciones(114,9.41998,-75.65075,"coveñas4"));
        return estaciones;
    }
}

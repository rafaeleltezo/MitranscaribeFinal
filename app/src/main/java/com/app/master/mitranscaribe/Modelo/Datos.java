package com.app.master.mitranscaribe.Modelo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Rafael p on 15/4/2017.
 */

public class Datos {

    public ArrayList<Estaciones> getPosicionEstaciones(){
        ArrayList<Estaciones>estaciones=new ArrayList<>();
        estaciones.add(new Estaciones("12",10.48966573,-75.4800319,"Castellana"));
        estaciones.add(new Estaciones("12",10.3896657,-75.480031,"Portal"));
        estaciones.add(new Estaciones("12",10.2896637,-75.4800329,"Bomba Gallo"));
        estaciones.add(new Estaciones("21",10.689662,-75.4800119,"Maria Auxiliadora"));
        return estaciones;
    }

}

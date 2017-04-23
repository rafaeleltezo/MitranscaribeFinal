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
        estaciones.add(new Estaciones("12",10.3901017,-75.480396,"Plazuela"));
        estaciones.add(new Estaciones("12",10.3842243,-75.4583624,"Portal"));
        estaciones.add(new Estaciones("12",10.3842240,-75.4583627,"Bomba Gallo"));
        estaciones.add(new Estaciones("21",10.3842249,-75.4583629,"Maria Auxiliadora"));
        return estaciones;
    }

}

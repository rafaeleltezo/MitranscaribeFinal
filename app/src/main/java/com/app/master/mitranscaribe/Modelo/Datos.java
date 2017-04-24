package com.app.master.mitranscaribe.Modelo;

import android.content.Context;
import android.widget.Toast;

import com.app.master.mitranscaribe.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael p on 15/4/2017.
 */

public class Datos {

    private ArrayList<Estaciones>estaciones;
    private ArrayList<Bus> buses;
    private Context context;
    public Datos(Context context){
        this.context=context;
    }
    public ArrayList<Estaciones> getPosicionEstaciones(){
        estaciones=new ArrayList<>();
        estaciones.add(new Estaciones("12",10.3901017,-75.480396,"Plazuela"));
        estaciones.add(new Estaciones("12",10.3842243,-75.4583624,"Portal"));
        estaciones.add(new Estaciones("12",10.3842240,-75.4583627,"Bomba Gallo"));
        estaciones.add(new Estaciones("21",10.3842249,-75.4583629,"Maria Auxiliadora"));
        return estaciones;
    }


    public ArrayList<Bus> getPosicionBus(){
        buses=new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferences.referencia_nombre);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(context,dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {

                   // Bus bus=datos.getValue(Bus.class);
                    //Toast.makeText(context, bus.getNombre(), Toast.LENGTH_SHORT).show();
                    //buses.add(bus);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return buses;
    }

}

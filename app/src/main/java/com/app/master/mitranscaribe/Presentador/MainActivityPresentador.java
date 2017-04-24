package com.app.master.mitranscaribe.Presentador;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.app.master.mitranscaribe.Modelo.Bus;
import com.app.master.mitranscaribe.Modelo.Datos;
import com.app.master.mitranscaribe.Modelo.Estaciones;
import com.app.master.mitranscaribe.Modelo.FirebaseReferences;
import com.app.master.mitranscaribe.R;
import com.app.master.mitranscaribe.Vista.iMainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Rafael p on 10/4/2017.
 */

public class MainActivityPresentador  implements iMainActivityPresentador{


    private Context context;
    private iMainActivity iMainActivity;
    private Datos datos;


    public MainActivityPresentador(iMainActivity iMainActivity, Context context) {
        this.iMainActivity = iMainActivity;
        this.context = context;


    }

    @Override
    public void establecerFragmentMapa() {
        iMainActivity.sincronizarMapa();

    }


    @Override
    public void establecerPermisos() {

                iMainActivity.establecerPermiso();

    }

    @Override
    public void chekerInternet() {
        if(!iMainActivity.chekearInternet()){
            Toast.makeText(context, "No tiene internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void agregarMiLocalizacion() {
        iMainActivity.miLocalizacion();

    }

    /*
        @Override
        public void establecerGooglePlay() {
            iMainActivity.getApiLocalizacion();
        }
        @Override
        public void actualizarUbicacion() {
            iMainActivity.actualizarLocalizacion();
        }

        @Override
        public void superposicion() {
            iMainActivity.superporicion();
        }

    */
    @Override
    public void agregarUbicacionEstacion() {
        datos=new Datos(context);
        for (Estaciones  estacion :datos.getPosicionEstaciones()) {
            iMainActivity.addPosicionEstacion(estacion.getLatitud(),estacion.getLongitud(),estacion.getNombre());

        }
    }

    @Override
    public void agregarUbicacionBuses(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferences.referencia_Bus);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                iMainActivity.refrescarMapa();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {
                    Bus bus=datos.getValue(Bus.class);
                    Toast.makeText(context, bus.getNombre(), Toast.LENGTH_SHORT).show();
                    iMainActivity.addPosicionEstacion(bus.getLatitud(),bus.getLongitud(),bus.getNombre());

                }
               //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context,database.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

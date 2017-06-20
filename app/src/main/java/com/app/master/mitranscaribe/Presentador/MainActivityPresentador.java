package com.app.master.mitranscaribe.Presentador;

import android.content.Context;
import android.widget.Toast;

import com.app.master.mitranscaribe.Modelo.Bus;
import com.app.master.mitranscaribe.Modelo.Coordenadas;
import com.app.master.mitranscaribe.Modelo.FirebaseReferences;
import com.app.master.mitranscaribe.Modelo.Paradero;
import com.app.master.mitranscaribe.Presentador.RestGoogleMaps.AdaptadorEnpointGoogle;
import com.app.master.mitranscaribe.Presentador.RestGoogleMaps.EnpointApiGoogleMaps;
import com.app.master.mitranscaribe.Presentador.RestGoogleMaps.RespuestaCoordenadas;
import com.app.master.mitranscaribe.Vista.iMainActivity;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rafael p on 10/4/2017.
 */

public class MainActivityPresentador  implements iMainActivityPresentador{

    private  FirebaseDatabase database;
    private Context context;
    private iMainActivity iMainActivity;
    private ArrayList<Marker>marcadores;
    private ArrayList<Coordenadas> coordenadasMapa;


    public MainActivityPresentador(iMainActivity iMainActivity, Context context) {
        this.iMainActivity = iMainActivity;
        this.context = context;


    }

    @Override
    public void establecerFragmentMapa() {
        iMainActivity.sincronizarMapa();

    }

    @Override
    public void limitesMapa() {
        iMainActivity.marcarLimites();
    }



    public ArrayList<Coordenadas> getCoordenadasMapa() {
        return coordenadasMapa;
    }

    public void setCoordenadasMapa(ArrayList<Coordenadas> coordenadasMapa) {
        this.coordenadasMapa = coordenadasMapa;
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


    @Override
    public void establecerGooglePlay() {
        iMainActivity.getApiLocalizacion();
           }

      @Override
      public void actualizarUbicacion() {
          iMainActivity.actualizarLocalizacion();
      }
/*
      @Override
      public void superposicion() {
          iMainActivity.superporicion();
      }

   */
    @Override
    public void agregarUbicacionEstacion() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferences.referencia_Paradero);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                iMainActivity.refrescarMarcadorParaderos();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {
                    Paradero paradero=datos.getValue(Paradero.class);
                    Toast.makeText(context,paradero.getNombre(), Toast.LENGTH_SHORT).show();
                    iMainActivity.addPosicionEstacion(paradero.getLatitud(),paradero.getLongitud(),
                            paradero.getNombre());

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context,database.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void agregarUbicacionBuses(){

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferences.referencia_Bus);
/*
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(context,"Agregaron "+ dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(context,s + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                Bus bus=dataSnapshot.getValue(Bus.class);
                iMainActivity.EstablecerFragementMapa();
                iMainActivity.addPosicionBus(bus.getLatitud(),bus.getLatitud(),bus.getNombre()
                        ,bus.getEstado(),dataSnapshot.getKey().toString());

                Toast.makeText(context, String.valueOf(bus.getLatitud()) , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(context,"Eliminaron "+ dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(context,"Movi "+ dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */


         myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                   iMainActivity.refrescarMarcadorBus();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {
                    Bus bus=datos.getValue(Bus.class);
                    //Toast.makeText(context, bus.getNombre(), Toast.LENGTH_SHORT).show();
                    iMainActivity.addPosicionBus(bus.getLatitud(),bus.getLongitud(),bus.getNombre(),bus.getEstado(),"");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context,database.toString(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void obtenerRutaGoogleMaps(double latitudOrigen,double longitudOrigen,double latitudDestino,double longitudDestino) {
        AdaptadorEnpointGoogle adaptador=new AdaptadorEnpointGoogle();
        Gson gson=adaptador.construyeJsonDeserializador();
        EnpointApiGoogleMaps enpoint=adaptador.establecerConexionGoogleMaps(gson);
        Call<RespuestaCoordenadas> coordenadas=enpoint.getUbicacion(latitudOrigen+","+longitudOrigen,latitudDestino+","+longitudDestino,"AIzaSyDjjRBHOHlbzcFrVl_xQAK07u0EZyr19YQ");
        coordenadas.enqueue(new Callback<RespuestaCoordenadas>() {
            @Override
            public void onResponse(Call<RespuestaCoordenadas> call, Response<RespuestaCoordenadas> response) {
                RespuestaCoordenadas respuesta=response.body();
                setCoordenadasMapa(respuesta.getCoordenadas());
                iMainActivity.ira(getCoordenadasMapa());
                Toast.makeText(context,String.valueOf(coordenadasMapa.get(0).getDistancia()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RespuestaCoordenadas> call, Throwable t) {
                Toast.makeText(context, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

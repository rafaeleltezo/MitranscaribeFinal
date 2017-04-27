package com.app.master.mitranscaribe.Presentador;

import android.content.Context;
import android.widget.Toast;

import com.app.master.mitranscaribe.Modelo.Bus;
import com.app.master.mitranscaribe.Modelo.FirebaseReferences;
import com.app.master.mitranscaribe.Modelo.Paradero;
import com.app.master.mitranscaribe.R;
import com.app.master.mitranscaribe.Vista.iMainActivity;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Rafael p on 10/4/2017.
 */

public class MainActivityPresentador  implements iMainActivityPresentador{

    private  FirebaseDatabase database;
    private Context context;
    private iMainActivity iMainActivity;



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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   iMainActivity.refrescarMarcadorBus();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {
                    Bus bus=datos.getValue(Bus.class);
                    Toast.makeText(context, bus.getNombre(), Toast.LENGTH_SHORT).show();
                    iMainActivity.addPosicionBus(bus.getLatitud(),bus.getLongitud(),bus.getNombre(),bus.getEstado());

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

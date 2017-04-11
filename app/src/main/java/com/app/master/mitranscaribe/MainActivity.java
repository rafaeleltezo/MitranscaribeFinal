package com.app.master.mitranscaribe;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.master.mitranscaribe.Presentador.MainActivityPresentador;
import com.app.master.mitranscaribe.Presentador.iMainActivityPresentador;
import com.app.master.mitranscaribe.Vista.iMainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, iMainActivity{
    private GoogleMap mapa;
    private iMainActivityPresentador presentador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Establecer servicios de localizacion de googleLocalizacion
        //fin establcer
        presentador= new MainActivityPresentador(this,this,this);
        if(!presentador.chequearPermiso()){
            presentador.establecerPermiso();
        }else {

            Toast.makeText(this,"Permiso de localizacion otorgado",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        configurarMapa(mapa);
    }

    @Override
    public void configurarMapa(GoogleMap mapa) {
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(10.397663 ,-75.502081),1);
        mapa.setMinZoomPreference(10);

        mapa.moveCamera(camUpd1);
    }

    @Override
    public MapFragment EstablecerFragementMapa() {
        return (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
    }

    @Override
    public void sincronizarMapa() {
        EstablecerFragementMapa().getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case MainActivityPresentador.CODIGO_PERMISO_LOCALIZACION:
                if(presentador.chequearPermiso()){
                    Toast.makeText(this,"Permiso de localizacion activo",Toast.LENGTH_LONG).show();
                }
                break;

        }
    }


}

package com.app.master.mitranscaribe;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, iMainActivity{
    private GoogleMap mapa;
    private iMainActivityPresentador presentador;
    private Button buttonSuelo;
    private Button buttonNormal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Agregarndo referencia de fragment al ActivityMAin
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Fin Agregando referencia Fragment
        presentador= new MainActivityPresentador(this,this,this);
        if(!presentador.chequearPermiso()){
            presentador.establecerPermiso();
        }else {

            Toast.makeText(this,"Permiso de localizacion otorgado",Toast.LENGTH_LONG).show();
        }

        //Configurando boton para ver desde el suelo
        try {
            buttonSuelo = (Button) findViewById(R.id.botonSuelo);
            buttonNormal = (Button) findViewById(R.id.botonNormal);
            buttonNormal.setVisibility(View.INVISIBLE);

            buttonSuelo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verDesdeSuelo();
                    buttonNormal.setVisibility(View.VISIBLE);
                    buttonSuelo.setEnabled(false);
                }
            });
            buttonNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configurarMapa();
                    buttonSuelo.setEnabled(true);
                    buttonNormal.setVisibility(View.INVISIBLE);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        configurarMapa();
        }


    @Override
    public void configurarMapa() {
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng ubicacion=new LatLng(10.397663 ,-75.502081);
        CameraPosition cameraPosition=new CameraPosition.Builder()
                .target(ubicacion)
                .zoom(15)
                .bearing(0)
                .tilt(0)
                .build();
        CameraUpdate camara=CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapa.animateCamera(camara);
    }

    @Override
    public void verDesdeSuelo() {
        LatLng ubicacion=new LatLng(10.397663 ,-75.502081);
        CameraPosition cameraPosition=new CameraPosition.Builder()
                .target(ubicacion)
                .zoom(19)
                .bearing(45)
                .tilt(90)
                .build();
        CameraUpdate camara=CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapa.animateCamera(camara);
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

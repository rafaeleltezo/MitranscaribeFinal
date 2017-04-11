package com.app.master.mitranscaribe.Presentador;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.app.master.mitranscaribe.Vista.iMainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Rafael p on 10/4/2017.
 */

public class MainActivityPresentador implements iMainActivityPresentador {


    private Context context;
    private iMainActivity iMainActivity;
    private Activity activity;
    public  static final int CODIGO_PERMISO_LOCALIZACION=1;

    public MainActivityPresentador(iMainActivity iMainActivity, Context context, Activity activity){
        this.iMainActivity=iMainActivity;
        this.context=context;
        this.activity=activity;
    }
    @Override
    public LatLng getCoordenadasUsuario() {

        return null;
    }

    @Override
    public void mostrarCoordenadasUsuario() {

    }

    @Override
    public boolean chequearPermiso() {
       int estado=ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION);
        if(estado== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        Toast.makeText(context,"Permiso Denegado",Toast.LENGTH_LONG).show();
        return false;
            }

    @Override
    public void establecerPermiso() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(context,"Permiso otorgado puedes cambiarlos en ajustes de aplicacion",Toast.LENGTH_LONG).show();
        }
        else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},this.CODIGO_PERMISO_LOCALIZACION);
        }
    }

    @Override
    public void agregarMarcadorUsuario() {

    }


}

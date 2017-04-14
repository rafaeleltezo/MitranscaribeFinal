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

/**
 * Created by Rafael p on 10/4/2017.
 */

public class MainActivityPresentador implements iMainActivityPresentador {


    private Context context;
    private iMainActivity iMainActivity;
    private Activity activity;


    public MainActivityPresentador(iMainActivity iMainActivity, Context context) {
        this.iMainActivity = iMainActivity;
        this.context = context;
        this.activity = activity;

    }

    @Override
    public void establecerFragmentMapa() {
        iMainActivity.sincronizarMapa();

    }

    @Override
    public void establecerGooglePlay() {
        iMainActivity.getApiLocalizacion();
    }

    @Override
    public void establecerPermisos() {
        if (!iMainActivity.chequearPermiso()) {
            iMainActivity.establecerPermiso();
        } else {

            Toast.makeText(context, "Permisos otorgados", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void actualizarUbicacion() {
        iMainActivity.actualizarLocalizacion();
    }

    @Override
    public void superposicion() {
        iMainActivity.superporicion();
    }


}

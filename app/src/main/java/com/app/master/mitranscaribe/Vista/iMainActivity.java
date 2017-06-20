package com.app.master.mitranscaribe.Vista;

import android.location.Location;

import com.app.master.mitranscaribe.Modelo.Coordenadas;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Rafael p on 10/4/2017.
 */

public interface iMainActivity {
    //public void superporicion();
    public boolean chequearPermiso();
    public void establecerPermiso();
    public void miLocalizacion();
    public void marcarLimites();
    public boolean chekearInternet();
    //public void refrescarMapa();
    public void agregarUbicacionBuses();
    public void getApiLocalizacion();
    //public void setLocalizacion(Location location);
    public void configurarMapa(double lat,double lon);
    //public void verDesdeSuelo(double lat,double lon);
    //public void verNormal(double lat,double lon);
    public MapFragment EstablecerFragementMapa();
    public void sincronizarMapa();
    public void actualizarLocalizacion();
    public void startLocationUpdates();
    public void desactivarLocalizacion();
    public void addPosicionBus(double latitud, double longitud, String titulo,String estado,String idMarcador);
    public void refrescarMarcadorBus();
    public void addPosicionEstacion(double latitud,double longitud,String titulo);
    public void refrescarMarcadorParaderos();
    public void ira(ArrayList<Coordenadas> coordenadases);

}


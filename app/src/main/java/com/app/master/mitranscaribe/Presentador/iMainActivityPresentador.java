package com.app.master.mitranscaribe.Presentador;

import android.location.Location;

import com.app.master.mitranscaribe.Modelo.Coordenadas;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Rafael p on 10/4/2017.
 */

public interface iMainActivityPresentador {
    public void establecerFragmentMapa();
    public void limitesMapa();
    public ArrayList<Coordenadas>obteberCoordenadas();
    public void establecerGooglePlay();
    public void establecerPermisos();
    public void actualizarUbicacion();
    //public void superposicion();
    public void chekerInternet();
    public void agregarMiLocalizacion();
    public void agregarUbicacionEstacion();
    public void agregarUbicacionBuses();
    public void obtenerRutaGoogleMaps(double latitudOrigen,double longitudOrigen,
                                      double latitudDestino,double longitudDestino);

}

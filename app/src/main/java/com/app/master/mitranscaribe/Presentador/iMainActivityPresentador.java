package com.app.master.mitranscaribe.Presentador;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Rafael p on 10/4/2017.
 */

public interface iMainActivityPresentador {
    public LatLng getCoordenadasUsuario();
    public void mostrarCoordenadasUsuario();
    public boolean chequearPermiso();
    public void establecerPermiso();
    public void agregarMarcadorUsuario(double lat,double lon);


}

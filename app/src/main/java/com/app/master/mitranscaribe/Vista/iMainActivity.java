package com.app.master.mitranscaribe.Vista;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by Rafael p on 10/4/2017.
 */

public interface iMainActivity {
    public void configurarMapa();
    public MapFragment EstablecerFragementMapa();
    public void sincronizarMapa();
    public void verDesdeSuelo();

}


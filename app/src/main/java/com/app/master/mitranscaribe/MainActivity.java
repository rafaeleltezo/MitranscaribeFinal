package com.app.master.mitranscaribe;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.master.mitranscaribe.Modelo.Bus;
import com.app.master.mitranscaribe.Modelo.Coordenadas;
import com.app.master.mitranscaribe.Modelo.FirebaseReferences;
import com.app.master.mitranscaribe.Presentador.MainActivityPresentador;
import com.app.master.mitranscaribe.Presentador.iMainActivityPresentador;
import com.app.master.mitranscaribe.Vista.iMainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;

import static com.app.master.mitranscaribe.R.id.btnActualizar;
import static com.app.master.mitranscaribe.R.id.design_menu_item_action_area;
import static com.app.master.mitranscaribe.R.id.map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, iMainActivity,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mapa;
    public static final int CODIGO_PERMISO_LOCALIZACION = 1;
    private iMainActivityPresentador presentador;
    private FloatingActionButton buttonNormal;
    private Marker marcadorBus;
    private Marker marcadorParadero;
    Marker marcador;
    private ArrayList<Marker> marcadoresBus;
    private ArrayList<Marker> marcadoresParadero;
    private GoogleApiClient apiClient;
    private LocationRequest locRequest;
    public static final int PETICION_CONFIG_UBICACION = 2;
    private AdRequest adRequest;
    private AdView adView;
    private Toolbar toolbar;

    /*
    private Location localizacion;

    private FloatingActionButton buttonSuelo;

    private LinearLayout lPrincipal;
    private double latitud;
    private double logitud;

    private MarkerOptions marcadorMiPosicion;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar=(Toolbar)findViewById(R.id.toolbar);
        //       toolbar.setTitle("");
        //setSupportActionBar(toolbar);
        presentador = new MainActivityPresentador(this, this);
        marcadoresBus = new ArrayList<>();
        marcadoresParadero = new ArrayList<>();
        presentador.establecerFragmentMapa();
        presentador.establecerPermisos();
        Toast.makeText(this, "Estoy aqui", Toast.LENGTH_SHORT).show();
        presentador.chekerInternet();



        //Agregarndo Publicidad
        adView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        //fin publicidad
        presentador.agregarUbicacionBuses();
        buttonNormal = (FloatingActionButton) findViewById(R.id.botonNormal);
        buttonNormal.setVisibility(View.INVISIBLE);
        buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presentador.obtenerRutaGoogleMaps(10.384199,-75.4583,10.3868033,-75.4809854);

            }
        });

        /*
        lPrincipal = (LinearLayout) findViewById(R.id.principal);
        buttonSuelo = (FloatingActionButton) findViewById(R.id.botonSuelo);

        buttonNormal.setVisibility(View.INVISIBLE);
        buttonSuelo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verDesdeSuelo(getLatitud(), getLogitud());
                Toast.makeText(getBaseContext(), "Latitud= " + getLatitud() + " Longitud= " + getLogitud(), Toast.LENGTH_LONG).show();
                buttonNormal.setVisibility(View.VISIBLE);
                // buttonSuelo.setEnabled(false);
                actualizarLocalizacion();


            }
        });
       */
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        configurarMapa(10.4027901, -75.5156382);
       /*
        if (chequearPermiso()) {
            presentador.agregarUbicacionEstacion();
            presentador.agregarUbicacionBuses();
            presentador.establecerGooglePlay();
            presentador.actualizarUbicacion();
            presentador.limitesMapa();
        }*/

    }

    public Marker getMarcadorBus() {
        return marcadorBus;
    }

    public void setMarcadorBus(Marker marcadorBus) {
        marcadoresBus.add(marcadorBus);
        this.marcadorBus = marcadorBus;
    }

    public Marker getMarcadorParadero() {
        return marcadorParadero;
    }

    public void setMarcadorParadero(Marker marcadorParadero) {
        marcadoresParadero.add(marcadorParadero);
        this.marcadorParadero = marcadorParadero;
    }

    @Override
    public void ira(ArrayList<Coordenadas> coordenadas) {
        PolylineOptions linea = new PolylineOptions();
        for (Coordenadas coordenada:coordenadas) {
            linea.add(new LatLng(coordenada.getLatitud(), coordenada.getLongitud()));
        }
        mapa.addPolyline(linea);



    }


    /* @Override
    public void superporicion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Toast.makeText(this, "Se necesita un permiso, acivelo a continuacion", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }
    }
*/
    @Override
    public boolean chequearPermiso() {
        int estadoACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int estadoACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (estadoACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED &&
                estadoACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_LONG).show();
        return false;
    }


    @Override
    public void establecerPermiso() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        CODIGO_PERMISO_LOCALIZACION);

            }
        }
        /*
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            Toast.makeText(this, "Permiso No otorgado, puedes cambiarlos en ajustes de aplicacion", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, this.CODIGO_PERMISO_LOCALIZACION);
        }*/
    }

    @Override
    public void miLocalizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            establecerPermiso();
            return;
        }
        mapa.setMyLocationEnabled(true);

    }

    @Override
    public void marcarLimites() {
        LatLngBounds Cartagena = new LatLngBounds(
                //10.4027901, -75.5156382
                new LatLng(10.3027, -75.6156), new LatLng(10.5627, -75.3156));
// Constrain the camera target to the Adelaide bounds.
        mapa.setLatLngBoundsForCameraTarget(Cartagena);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISO_LOCALIZACION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presentador.agregarUbicacionEstacion();
                    presentador.agregarUbicacionBuses();
                    presentador.establecerGooglePlay();
                    presentador.actualizarUbicacion();
                    presentador.limitesMapa();
                    Toast.makeText(this, "Permiso de localizacion activo", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "No esta activo el permiso", Toast.LENGTH_SHORT).show();
                    presentador.establecerPermisos();
                }
                break;

        }
    }

    public boolean chekearInternet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void agregarUbicacionBuses() {

    }


    @Override
    public void getApiLocalizacion() {
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    /*
                @Override
                public void setLocalizacion(Location location) {
                    this.localizacion=location;
                    if (location != null) {
                        setLogitud(location.getLongitude());
                        setLatitud(location.getLatitude());
                        Toast.makeText(this, "Latitud= "+location.getLatitude()+" Longitud= "+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(lPrincipal, "Ubicacion Desconocida", Snackbar.LENGTH_LONG).show();
                    }
                }

                public double getLatitud() {
                    return latitud;
                }

                public void setLatitud(double latitud) {
                    this.latitud = latitud;
                }

                public double getLogitud() {
                    return logitud;
                }

                public void setLogitud(double logitud) {
                    this.logitud = logitud;
                }
            */
    @Override
    public void configurarMapa(double lat, double lon) {
        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                buttonNormal.setVisibility(View.VISIBLE);
                setMarcadorParadero(marker);
                return false;


            }

        });
        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                buttonNormal.setVisibility(View.INVISIBLE);
            }
        });
        UiSettings u = mapa.getUiSettings();
        u.setMapToolbarEnabled(false);


        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(14)
                .bearing(0)
                .tilt(0)
                .build();
        CameraUpdate camara = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapa.animateCamera(camara);

        mapa.setMinZoomPreference(11f);
        mapa.setMaxZoomPreference(18f);

    }

    /*
        @Override
        public void verDesdeSuelo(double lat, double lon) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lon))
                    .zoom(19)
                    .bearing(45)
                    .tilt(90)
                    .build();
            CameraUpdate camara = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mapa.animateCamera(camara);
        }

        @Override
        public void verNormal(double lat, double lon) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(lat, lon))
                        .zoom(15)
                        .bearing(0)
                        .tilt(0)
                        .build();
                CameraUpdate camara = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mapa.animateCamera(camara);

        }
    */
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
    public void actualizarLocalizacion() {
        locRequest = new LocationRequest();
        locRequest.setInterval(20000);
        locRequest.setFastestInterval(10000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//           startLocationUpdates();
        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();
                        presentador.agregarMiLocalizacion();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Log.i(LOGTAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(MainActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            //btnActualizar.setChecked(false);
                            //Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");
                        // btnActualizar.setChecked(false);
                        break;
                }
            }
        });
    }

    @Override
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            //Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locRequest, MainActivity.this);

        } else {
            establecerPermiso();
        }
    }

    @Override
    public void desactivarLocalizacion() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);
    }

    @Override
    public void addPosicionEstacion(double latitud, double longitud, String titulo) {

        Marker marker = mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(titulo));
        setMarcadorParadero(marker);
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.estacion));


    }

    @Override
    public void refrescarMarcadorParaderos() {
        if (marcadoresParadero.size() > 0) {
            for (Marker marcador : marcadoresParadero) {
                marcador.remove();
            }
            marcadoresParadero.clear();
        }

    }

    @Override
    public void addPosicionBus(double latitud, double longitud, String titulo, String estado, String idMarcador) {

        Marker marker = mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(titulo));
        marker.setTag(idMarcador);
        setMarcadorBus(marker);
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.gpsbus));


    }

    @Override
    public void refrescarMarcadorBus() {
        if (marcadoresBus.size() > 0) {
            for (Marker marcador : marcadoresBus) {
                marcador.remove();
            }
            marcadoresBus.clear();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Error Grave al conectar los Servicios de Google Play", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Active los permisos solicitados", Toast.LENGTH_SHORT).show();
            establecerPermiso();
            return;
        }
        Location lastLocation =
                LocationServices.FusedLocationApi.getLastLocation(apiClient);


        configurarMapa(lastLocation.getLatitude(), lastLocation.getLongitude());
        Toast.makeText(this,"latitud: "+ String.valueOf(lastLocation.getLatitude())+"longitud: "+String.valueOf(lastLocation.getLongitude()), Toast.LENGTH_SHORT).show();
        //setLocalizacion(lastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Se ha interrumpido la conexión con Google Play Services", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        actualizarLocalizacion();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this,
                                "El usuario no ha realizado los cambios de configuración necesarios",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Aplicacion pausada", Toast.LENGTH_SHORT).show();
        desactivarLocalizacion();
    }*/



}
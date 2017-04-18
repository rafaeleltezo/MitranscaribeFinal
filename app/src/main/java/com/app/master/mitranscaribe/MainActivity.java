package com.app.master.mitranscaribe;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.master.mitranscaribe.Presentador.MainActivityPresentador;
import com.app.master.mitranscaribe.Presentador.iMainActivityPresentador;
import com.app.master.mitranscaribe.Vista.iMainActivity;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.app.master.mitranscaribe.R.id.btnActualizar;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, iMainActivity, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,LocationListener {
    private GoogleMap mapa;
    private Location localizacion;
    private GoogleApiClient apiClient;
    public static final int CODIGO_PERMISO_LOCALIZACION = 1;
    public static final int PETICION_CONFIG_UBICACION=2;
    private iMainActivityPresentador presentador;
    private FloatingActionButton buttonSuelo;
    private FloatingActionButton buttonNormal;
    private LinearLayout lPrincipal;
    private double latitud;
    private double logitud;
    private LocationRequest locRequest;
    private MarkerOptions marcadorMiPosicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Agregarndo referencia de fragment al ActivityMAin
        presentador = new MainActivityPresentador(this,this);
        presentador.establecerFragmentMapa();
        presentador.establecerGooglePlay();
        presentador.establecerPermisos();


      //  presentador.superposicion();
        //Fin Agregando referencia Fragment

        //Configurando boton para ver desde el suelo

        lPrincipal = (LinearLayout) findViewById(R.id.principal);
        buttonSuelo = (FloatingActionButton) findViewById(R.id.botonSuelo);
        buttonNormal = (FloatingActionButton) findViewById(R.id.botonNormal);
        buttonNormal.setVisibility(View.INVISIBLE);
        buttonSuelo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verDesdeSuelo(getLatitud(),getLogitud());
                Toast.makeText(getBaseContext(),"Latitud= "+getLatitud()+" Longitud= "+getLogitud(),Toast.LENGTH_LONG).show();
                buttonNormal.setVisibility(View.VISIBLE);
               // buttonSuelo.setEnabled(false);
                actualizarLocalizacion();

               /* MarkerOptions marcador=new MarkerOptions();
                    mapa.addMarker(marcador.position(new LatLng(getLatitud(), getLogitud())).title(getString(R.string.miUbicacion)))
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.punto_de_marcador_de_posicion_lleno));*/


            }
        });
        buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verNormal(getLatitud(),getLogitud());
                buttonSuelo.setEnabled(true);
                buttonNormal.setVisibility(View.INVISIBLE);
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        configurarMapa(10.410664  ,-75.520964);
        presentador.agregarUbicacionEstacion();
    }

    //investigar superpocision de permisoso
    @Override
    public void superporicion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Toast.makeText(this, "Se necesita un permiso, acivelo a continuacion", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }
    }

    @Override
    public boolean chequearPermiso() {
        int estadoACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int estadoSYSTEM_ALERT_WINDOW = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
        int estadoACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (estadoACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED &&
                estadoACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED&&
                estadoSYSTEM_ALERT_WINDOW == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void establecerPermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW)) {

            Toast.makeText(this, "Permiso otorgado, puedes cambiarlos en ajustes de aplicacion", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SYSTEM_ALERT_WINDOW},
                    this.CODIGO_PERMISO_LOCALIZACION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case CODIGO_PERMISO_LOCALIZACION:
                if (chequearPermiso()) {
                    Toast.makeText(this, "Permiso de localizacion activo", Toast.LENGTH_LONG).show();
                }else {
                    establecerPermiso();
                }
                break;

        }
    }

    @Override
    public void getApiLocalizacion() {
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

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

    @Override
    public void configurarMapa(double lat, double lon) {
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(12)
                .bearing(0)
                .tilt(0)
                .build();
        CameraUpdate camara = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapa.animateCamera(camara);
    }

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
        locRequest.setInterval(2000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

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
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Toast.makeText(getBaseContext(),
                                    "Se Requiere Configuracion Adicional",Toast.LENGTH_LONG).show();
                            status.startResolutionForResult(MainActivity.this, PETICION_CONFIG_UBICACION);

                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(getBaseContext(),
                                    "Error al intentar solucionar configuración de ubicación",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(getBaseContext(), "El dispositivo no cuenta con lo requerido",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
    });
    }

    @Override
    public void startLocationUpdates() {
        int estadoACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int estadoACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int estadoSYSTEM_ALERT_WINDOW = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
        if (estadoACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED &&
                estadoACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED &&
                estadoSYSTEM_ALERT_WINDOW ==PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, MainActivity.this);

        }else {
            establecerPermiso();
        }

    }

    @Override
    public void addPosicionEstacion(double latitud, double longitud,String tituloEstacion) {
        mapa.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).title(tituloEstacion))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.parada_de_autobus));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(lPrincipal, "Error Grave al conectar los Servicios de Google Play", Snackbar.LENGTH_LONG).show();
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
        Toast.makeText(this, lastLocation.toString()+" entre", Toast.LENGTH_SHORT).show();
        setLocalizacion(lastLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Snackbar.make(lPrincipal, "Se ha interrumpido la conexión con Google Play Services", Snackbar.LENGTH_LONG).show();
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
        Toast.makeText(this, "Ubicacion recibida", Toast.LENGTH_SHORT).show();
        setLocalizacion(location);


            marcadorMiPosicion = new MarkerOptions();
            mapa.addMarker(marcadorMiPosicion.position(new LatLng(getLatitud(), getLogitud())).title(getString(R.string.miUbicacion)))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.punto_de_marcador_de_posicion_lleno));





    }
}

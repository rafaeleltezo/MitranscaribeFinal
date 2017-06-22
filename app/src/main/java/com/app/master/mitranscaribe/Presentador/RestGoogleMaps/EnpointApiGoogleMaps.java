package com.app.master.mitranscaribe.Presentador.RestGoogleMaps;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rafael p on 19/6/2017.
 */

public interface EnpointApiGoogleMaps {



    @GET("/maps/api/directions/json")

    Call<RespuestaCoordenadas> getUbicacion(@Query("origin") String origin,@Query("destination")
            String destination,@Query("key") String key,@Query("mode") String modoViaje);


}

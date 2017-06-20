package com.app.master.mitranscaribe.Presentador.RestGoogleMaps;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafael p on 19/6/2017.
 */

public class AdaptadorEnpointGoogle {

    public EnpointApiGoogleMaps establecerConexionGoogleMaps(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ConstantesUrl.URLBASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(EnpointApiGoogleMaps.class);
    }
}

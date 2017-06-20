package com.app.master.mitranscaribe.Presentador.RestGoogleMaps;

import com.app.master.mitranscaribe.Presentador.RestGoogleMaps.DeserializadorJsonGoogle.CoordenadasDeserializador;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafael p on 19/6/2017.
 */

public class AdaptadorEnpointGoogle {

    public EnpointApiGoogleMaps establecerConexionGoogleMaps(Gson gson){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ConstantesUrl.URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(EnpointApiGoogleMaps.class);
    }

    public Gson construyeJsonDeserializador(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RespuestaCoordenadas.class,new CoordenadasDeserializador());
        return gsonBuilder.create();
    }
}

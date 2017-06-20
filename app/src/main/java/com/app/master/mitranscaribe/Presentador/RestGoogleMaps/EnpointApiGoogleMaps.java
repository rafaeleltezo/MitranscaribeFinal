package com.app.master.mitranscaribe.Presentador.RestGoogleMaps;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rafael p on 19/6/2017.
 */

public interface EnpointApiGoogleMaps {



    @GET(ConstantesUrl.URLBASE+ConstantesUrl.ORIGEN+"{latitudOrigen}"+","+"{longitudOrigen}"
            +ConstantesUrl.CONCATENAR_VARIABLE+ConstantesUrl.DESTINO+"{latitudDestino}"+","+"{longitudDestino}"
            +ConstantesUrl.TOKEN)

    Call<RespuestaCoordenadas> getUbicacion(@Path("latitudOrigen") String latitudOrigen,@Path("longitudOrigen")
            String longitudOrigen,@Path("latitudDestino") String latitudDestino,@Path("longitudDestino")String longitudDestino);


}

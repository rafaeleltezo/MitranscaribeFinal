package com.app.master.mitranscaribe.Presentador.RestGoogleMaps.DeserializadorJsonGoogle;

import com.app.master.mitranscaribe.Modelo.Coordenadas;
import com.app.master.mitranscaribe.Presentador.RestGoogleMaps.RespuestaCoordenadas;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Rafael p on 19/6/2017.
 */

public class CoordenadasDeserializador implements JsonDeserializer<RespuestaCoordenadas> {

    @Override
    public RespuestaCoordenadas deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson=new Gson();
        RespuestaCoordenadas respuesta = gson.fromJson(json,RespuestaCoordenadas.class);
        JsonArray contactoResponData= json.getAsJsonObject().getAsJsonArray(ConstantesJsongoogleMaps.DATOS);


    }

    private ArrayList<Coordenadas> deserializador(JsonArray jsonArray){
        JsonObject objeto=jsonArray.get(i).getAsJsonObject();
        JsonObject
        ArrayList<Coordenadas>coordenadases=new ArrayList();
        for (int i = 0; i <jsonArray.size() ; i++) {

        }
    }
}

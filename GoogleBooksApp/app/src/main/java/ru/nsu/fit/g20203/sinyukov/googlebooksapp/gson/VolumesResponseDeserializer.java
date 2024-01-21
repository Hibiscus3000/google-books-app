package ru.nsu.fit.g20203.sinyukov.googlebooksapp.gson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;

public class VolumesResponseDeserializer implements JsonDeserializer<Volume[]> {

    @Override
    public Volume[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        return gson.fromJson(json.getAsJsonObject().get("items").getAsJsonArray(), Volume[].class);
    }
}
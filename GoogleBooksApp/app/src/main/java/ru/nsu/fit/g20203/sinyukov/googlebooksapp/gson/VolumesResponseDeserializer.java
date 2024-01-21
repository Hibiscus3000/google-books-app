package ru.nsu.fit.g20203.sinyukov.googlebooksapp.gson;

import android.util.Log;

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

    private static final String TAG = "VolumesResponseDeserializer";

    @Override
    public Volume[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        if (null == json) {
            return new Volume[0];
        }
        Log.i(TAG, "Received " + gson.fromJson(json.getAsJsonObject().get("totalItems"), Integer.class) + " total items");
        final JsonElement items = json.getAsJsonObject().get("items");
        if (null == items) {
            return new Volume[0];
        }
        return gson.fromJson(items.getAsJsonArray(), Volume[].class);
    }
}
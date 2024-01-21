package ru.nsu.fit.g20203.sinyukov.googlebooksapp.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Collection;
import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;

public class GoogleBooksGsonConverter {

    public static GsonConverterFactory buildGsonConverterFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Volume[].class, new VolumesResponseDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}

package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;

public interface GoogleBooksService {
    @GET("v1/volumes")
    Call<Volume[]> listVolumes(@QueryMap Map<String, String> queryParams);
}

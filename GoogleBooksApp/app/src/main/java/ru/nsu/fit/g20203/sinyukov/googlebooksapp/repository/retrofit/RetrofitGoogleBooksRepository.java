package ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.retrofit;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.PagedVolumeSearchResponse;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.gson.GoogleBooksGsonConverter;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.GoogleBooksService;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.GoogleBooksRepository;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.callback.GoogleBooksRepositoryCallback;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.callback.OnFailureParams;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.callback.OnResponseParams;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.PagedVolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.sourcetype.RetrofitSourceType;

public class RetrofitGoogleBooksRepository implements GoogleBooksRepository<RetrofitSourceType> {

    private static final String BASE_URL = "https://www.googleapis.com/books/";
    private final GoogleBooksService googleBooksService;

    private static final int NUMBER_OF_THREADS = 4;
    private final ListeningExecutorService listeningExecutorService;

    public RetrofitGoogleBooksRepository() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GoogleBooksGsonConverter.buildGsonConverterFactory())
                .build();
        googleBooksService = retrofit.create(GoogleBooksService.class);

        final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    }

    @Override
    public void listBooks(PagedVolumesRequest pagedVolumesRequest, GoogleBooksRepositoryCallback<RetrofitSourceType> callback) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ListenableFuture<PagedVolumeSearchResponse> listBooks(PagedVolumesRequest pagedVolumesRequest) {
        final Map<String, String> queryMap =
                RetrofitBooksRequestToQueryConverter.booksRequest2queryMap(pagedVolumesRequest);
        return listeningExecutorService.submit(() -> {
            Response<Volume[]> response = googleBooksService.listVolumes(queryMap).execute();
            return new PagedVolumeSearchResponse(response.body(), pagedVolumesRequest.getPageNumber() + 1);
        });
    }

    public static class RetrofitGoogleBooksCallback
            implements GoogleBooksRepositoryCallback<RetrofitSourceType> {

        private final Callback<List<Volume>> callback;

        public RetrofitGoogleBooksCallback(Callback<List<Volume>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(OnResponseParams<RetrofitSourceType> params) {
            final RetrofitOnResponseParams retrofitParams = (RetrofitOnResponseParams) params;
            callback.onResponse(retrofitParams.call, retrofitParams.response);
        }

        @Override
        public void onFailure(OnFailureParams<RetrofitSourceType> params) {
            final RetrofitOnFailureParams retrofitParams = (RetrofitOnFailureParams) params;
            callback.onFailure(retrofitParams.call, retrofitParams.t);
        }

        public static class RetrofitOnResponseParams implements OnResponseParams<RetrofitSourceType> {
            private final Call<List<Volume>> call;
            private final Response<List<Volume>> response;

            public RetrofitOnResponseParams(Call<List<Volume>> call, Response<List<Volume>> response) {
                this.call = call;
                this.response = response;
            }
        }

        public static class RetrofitOnFailureParams implements OnFailureParams<RetrofitSourceType> {
            private final Call<List<Volume>> call;
            private final Throwable t;

            public RetrofitOnFailureParams(Call<List<Volume>> call, Throwable t) {
                this.call = call;
                this.t = t;
            }
        }
    }
}

package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.GoogleBooksService;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository.GoogleBooksRepository;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository.GoogleBooksRepositoryCallback;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository.OnFailureParams;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository.OnResponseParams;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.BooksRequest;

public class RetrofitGoogleBooksRepository implements GoogleBooksRepository<RetrofitBooksRequest,
        RetrofitGoogleBooksRepository.RetrofitGoogleBooksCallback> {

    private static final String BASE_URL = "https://www.googleapis.com/books/";
    private final GoogleBooksService googleBooksService;

    public RetrofitGoogleBooksRepository() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        googleBooksService = retrofit.create(GoogleBooksService.class);
    }

    @Override
    public void listBooks(RetrofitBooksRequest booksRequest, RetrofitGoogleBooksCallback callback) {

    }

    public static class RetrofitGoogleBooksCallback
            implements GoogleBooksRepositoryCallback<RetrofitGoogleBooksCallback.RetrofitOnResponseParams,
                        RetrofitGoogleBooksCallback.RetrofitOnFailureParams> {

        private final Callback<List<Volume>> callback;

        public RetrofitGoogleBooksCallback(Callback<List<Volume>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(RetrofitOnResponseParams params) {
            callback.onResponse(params.call, params.response);
        }

        @Override
        public void onFailure(RetrofitOnFailureParams params) {
            callback.onFailure(params.call, params.t);
        }

        public static class RetrofitOnResponseParams implements OnResponseParams {
            private final Call<List<Volume>> call;
            private final Response<List<Volume>> response;

            public RetrofitOnResponseParams(Call<List<Volume>> call, Response<List<Volume>> response) {
                this.call = call;
                this.response = response;
            }
        }

        public static class RetrofitOnFailureParams implements OnFailureParams {
            private final Call<List<Volume>> call;
            private final Throwable t;

            public RetrofitOnFailureParams(Call<List<Volume>> call, Throwable t) {
                this.call = call;
                this.t = t;
            }
        }
    }
}

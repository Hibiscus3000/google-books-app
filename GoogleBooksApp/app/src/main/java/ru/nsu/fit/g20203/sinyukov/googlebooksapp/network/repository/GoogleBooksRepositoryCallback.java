package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository;

public interface GoogleBooksRepositoryCallback<R extends OnResponseParams,
        F extends OnFailureParams> {
    void onResponse(R params);
    void onFailure(F params);
}

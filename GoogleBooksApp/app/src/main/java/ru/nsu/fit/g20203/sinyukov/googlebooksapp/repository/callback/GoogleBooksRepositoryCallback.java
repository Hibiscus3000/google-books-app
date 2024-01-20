package ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.callback;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.sourcetype.SourceType;

public interface GoogleBooksRepositoryCallback<T extends SourceType> {
    void onResponse(OnResponseParams<T> params);

    void onFailure(OnFailureParams<T> params);
}

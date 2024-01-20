package ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository;

import com.google.common.util.concurrent.ListenableFuture;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.PagedVolumeSearchResponse;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.callback.GoogleBooksRepositoryCallback;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.PagedVolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.sourcetype.SourceType;

public interface GoogleBooksRepository<T extends SourceType> {

    void listBooks(PagedVolumesRequest volumesRequest, GoogleBooksRepositoryCallback<T> callback);

    ListenableFuture<PagedVolumeSearchResponse> listBooks(PagedVolumesRequest booksRequest);

}

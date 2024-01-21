package ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource;

import javax.annotation.Nullable;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.GoogleBooksRepository;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.sourcetype.SourceType;

public class PagingSourceFactory<T extends SourceType> {

    private final GoogleBooksRepository<T> repository;

    public PagingSourceFactory(GoogleBooksRepository<T> repository) {
        this.repository = repository;
    }

    public VolumesPagingSource invoke(@Nullable VolumesRequest volumesRequest) {
        if (null == volumesRequest) {
            return new EmptyVolumesPagingSource();
        }
        return new OnRequestVolumesPagingSource<>(volumesRequest, repository);
    }
}

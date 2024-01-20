package ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.GoogleBooksRepository;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.sourcetype.SourceType;

public class PagingSourceFactory<T extends SourceType> {

    private final GoogleBooksRepository<T> repository;

    public PagingSourceFactory(GoogleBooksRepository<T> repository) {
        this.repository = repository;
    }

    public VolumesDataSource<T> invoke(VolumesRequest volumesRequest) {
        return new VolumesDataSource<>(volumesRequest, repository);
    }
}

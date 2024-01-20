package ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.PagedVolumeSearchResponse;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.GoogleBooksRepository;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.PagedVolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.sourcetype.SourceType;

public class VolumesDataSource<T extends SourceType> extends ListenableFuturePagingSource<Integer, Volume> {

    private static final int NUMBER_OF_THREADS = 2;
    private final Executor executor = Executors.newCachedThreadPool();

    private final VolumesRequest volumesRequest;
    private final GoogleBooksRepository<T> repository;

    public VolumesDataSource(VolumesRequest volumesRequest, GoogleBooksRepository<T> repository) {
        this.volumesRequest = volumesRequest;
        this.repository = repository;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Volume> pagingState) {
        final Integer anchorPosition = pagingState.getAnchorPosition();
        if (null == anchorPosition) {
            return null;
        }

        final LoadResult.Page<Integer, Volume> anchorPage =
                pagingState.closestPageToPosition(anchorPosition);
        if (null == anchorPage) {
            return null;
        }

        final Integer prevKey = anchorPage.getPrevKey();
        if (null != prevKey) {
            return prevKey + 1;
        }

        final Integer nextKey = anchorPage.getNextKey();
        if (null != nextKey) {
            return nextKey - 1;
        }

        return null;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, Volume>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
        Integer key = loadParams.getKey();
        final int nextPageNumber = null == key ? 1 : key;

        ListenableFuture<PagedVolumeSearchResponse> futureResponse =
                repository.listBooks(new PagedVolumesRequest(volumesRequest, nextPageNumber));
        ListenableFuture<LoadResult<Integer, Volume>> pageFuture =
                Futures.transform(futureResponse, this::toLoadResult, executor);

        return Futures.catching(pageFuture, IOException.class, LoadResult.Error::new, executor);
    }

    private LoadResult<Integer, Volume> toLoadResult(@NotNull PagedVolumeSearchResponse response) {
        return new LoadResult.Page<>(response.getVolumes(),
                null /*TODO*/,
                response.getNextPageNumber(),
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}

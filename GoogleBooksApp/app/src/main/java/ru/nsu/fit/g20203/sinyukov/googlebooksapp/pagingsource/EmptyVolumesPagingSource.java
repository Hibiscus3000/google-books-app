package ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.List;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;

public class EmptyVolumesPagingSource extends VolumesPagingSource {

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Volume> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, Volume>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
        return Futures.immediateFuture(new LoadResult.Page<>(Collections.emptyList(),
                null,
                null,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED));
    }
}

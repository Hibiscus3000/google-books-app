package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.PositionalDataSource;

import kotlin.coroutines.Continuation;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;

public class BooksDataSource extends PagingSource<Integer, Volume> {

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Volume> pagingState) {

    }

    @Nullable
    @Override
    public Object load(@NonNull LoadParams<Integer> loadParams, @NonNull Continuation<? super LoadResult<Integer, Volume>> continuation) {
        Integer key = loadParams.getKey();
        final int index = null == key ? 0 : key;

    }
}

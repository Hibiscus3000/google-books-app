package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingLiveData;

import kotlinx.coroutines.CoroutineScope;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource.PagingSourceFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;

public class GoogleBooksViewModel extends ViewModel {

    private final MutableLiveData<VolumesRequest> volumesRequest = new MutableLiveData<>(null);

    public GoogleBooksViewModel(PagingSourceFactory<?> pagingSourceFactory) {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Volume> pager = new Pager<>(
                PagingConfig.getPagingConfig(),
                () -> pagingSourceFactory.invoke(volumesRequest.getValue())
        );

        PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }
}

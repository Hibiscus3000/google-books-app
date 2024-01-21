package ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import kotlinx.coroutines.CoroutineScope;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.PagingConfig;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.Volume;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource.PagingSourceFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;

public class VolumesViewModel extends ViewModel {

    private static final String TAG = "VolumesViewModel";

    private final MutableLiveData<VolumesRequest> volumesRequest = new MutableLiveData<>();
    private final MutableLiveData<Boolean> noResults = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> endOfPaginationReached = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> errorOccurred = new MutableLiveData<>(false);

    private final PagingSourceFactory<?> pagingSourceFactory;
    private final Pager<Integer, Volume> pager;

    public VolumesViewModel(PagingSourceFactory<?> pagingSourceFactory) {
        pager = new Pager<>(
                PagingConfig.getPagingConfig(),
                () -> pagingSourceFactory.invoke(volumesRequest.getValue())
        );

        this.pagingSourceFactory = pagingSourceFactory;

        final CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<Volume>> getPagerLiveData() {
        return PagingLiveData.getLiveData(pager);
    }

    public LiveData<VolumesRequest> getVolumesRequestLiveData() {
        return volumesRequest;
    }

    public void setVolumesRequest(@NonNull VolumesRequest volumesRequest) {
        if (!volumesRequest.isValid()) {
            throw new RuntimeException("Volumes request invalid in adapter");
        }
        Log.i(TAG, "New request: " + volumesRequest);
        this.volumesRequest.postValue(volumesRequest);
    }

    public void setNoResults(boolean b) {
        if (pagingSourceFactory.lastSourceIsNotEmpty()) {
            noResults.postValue(b);
        }
    }

    public void setEndOfPaginationReached(boolean b) {
        if (pagingSourceFactory.lastSourceIsNotEmpty()) {
            endOfPaginationReached.postValue(b);
        }
    }

    public void setErrorOccurred(boolean b) {
        if (pagingSourceFactory.lastSourceIsNotEmpty()) {
            errorOccurred.postValue(b);
        }
    }

    public LiveData<Boolean> getNoResultsLiveData() {
        return noResults;
    }

    public LiveData<Boolean> getEndOfPaginationReachedLiveData() {
        return endOfPaginationReached;
    }

    public LiveData<Boolean> getErrorOccurredLiveData() {
        return errorOccurred;
    }

    public void clear() {
        noResults.postValue(false);
        endOfPaginationReached.postValue(false);
        errorOccurred.postValue(false);
    }
}

package ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel;

import android.nfc.Tag;
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

    private Pager<Integer, Volume> pager;

    public VolumesViewModel(PagingSourceFactory<?> pagingSourceFactory) {
        pager = new Pager<>(
                PagingConfig.getPagingConfig(),
                () -> pagingSourceFactory.invoke(volumesRequest.getValue())
        );

        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
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
}

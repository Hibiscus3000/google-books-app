package ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.pagingsource.PagingSourceFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.repository.retrofit.RetrofitGoogleBooksRepository;

public class RetrofitVolumesViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new VolumesViewModel(new PagingSourceFactory<>(new RetrofitGoogleBooksRepository()));
    }
}

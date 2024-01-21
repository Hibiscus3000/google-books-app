package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListActivityBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel.VolumesViewModel;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel.RetrofitVolumesViewModelFactory;

public class ListActivity extends AppCompatActivity {

    private ListActivityBinding binding;

    private VolumesViewModel volumesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ListActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        volumesViewModel = new ViewModelProvider(this, new RetrofitVolumesViewModelFactory())
                .get(VolumesViewModel.class);

        final VolumesAdapter volumesAdapter = new VolumesAdapter(new VolumeComparator());
        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(volumesAdapter);
        volumesViewModel.getPagerLiveData().observe(this, volumePagingData ->
                volumesAdapter.submitData(getLifecycle(), volumePagingData));

        volumesViewModel.getVolumesRequestLiveData().observe(this, volumePagingData -> volumesAdapter.refresh());

        binding.titleEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                search();
                handled = true;
            }
            return handled;
        });
    }

    private void search() {
        final VolumesRequest volumesRequest = createRequest();
        if (volumesRequest.isValid()) {
            volumesViewModel.setVolumesRequest(volumesRequest);
        }
    }

    private VolumesRequest createRequest() {
        final VolumesRequest volumesRequest = getSupportFragmentManager().findFragmentById(R.layout.);
    }
}
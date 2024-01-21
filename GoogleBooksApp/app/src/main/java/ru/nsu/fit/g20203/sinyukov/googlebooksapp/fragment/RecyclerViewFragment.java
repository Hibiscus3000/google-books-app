package ru.nsu.fit.g20203.sinyukov.googlebooksapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.R;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.VolumeComparator;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.VolumesAdapter;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.SearchRecyclerViewFragmentBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel.RetrofitVolumesViewModelFactory;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.viewmodel.VolumesViewModel;

public class RecyclerViewFragment extends Fragment {

    private VolumesViewModel volumesViewModel;

    public RecyclerViewFragment() {
        super(R.layout.search_recycler_view_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        volumesViewModel = new ViewModelProvider(requireActivity(), new RetrofitVolumesViewModelFactory()).get(VolumesViewModel.class);

        final SearchRecyclerViewFragmentBinding binding = SearchRecyclerViewFragmentBinding.inflate(inflater, container, false);
        final View view = binding.getRoot();

        final VolumesAdapter volumesAdapter = new VolumesAdapter(new VolumeComparator());
        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(volumesAdapter);
        volumesViewModel.getPagerLiveData().observe(getViewLifecycleOwner(), volumePagingData ->
                volumesAdapter.submitData(getLifecycle(), volumePagingData));

        volumesAdapter.addLoadStateListener(loadState -> {
            if (loadState.getSource().getRefresh() instanceof LoadState.Error) {
                volumesViewModel.setErrorOccurred(true);
            } else {
                volumesViewModel.setErrorOccurred(true);
            }
            if (loadState.getSource().getRefresh() instanceof LoadState.NotLoading
                    && loadState.getAppend().getEndOfPaginationReached()) {
                if (volumesAdapter.getItemCount() >= 1) {
                    volumesViewModel.setEndOfPaginationReached(true);
                    volumesViewModel.setNoResults(false);
                } else {
                    volumesViewModel.setEndOfPaginationReached(false);
                    volumesViewModel.setNoResults(true);
                }
            } else {
                volumesViewModel.setEndOfPaginationReached(false);
                volumesViewModel.setNoResults(false);
            }
            return null;
        });

        volumesViewModel.getVolumesRequestLiveData().observe(getViewLifecycleOwner(), volumePagingData -> volumesAdapter.refresh());

        return view;
    }
}

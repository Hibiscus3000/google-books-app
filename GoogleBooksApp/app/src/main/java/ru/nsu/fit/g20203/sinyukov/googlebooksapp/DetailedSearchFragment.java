package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.DetailedSearchFragmentBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.PrintType;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.request.VolumesRequest;

public class DetailedSearchFragment extends Fragment {

    private DetailedSearchFragmentBinding binding;

    public DetailedSearchFragment() {
        super(R.layout.detailed_search_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        binding = DetailedSearchFragmentBinding.inflate(inflater, container, false);
        return view;
    }

    public VolumesRequest getVolumesRequest() {
        final VolumesRequest volumesRequest = new VolumesRequest();
        volumesRequest.setAuthor(binding.authorEditText.getText().toString());
        volumesRequest.setPublisher(binding.publisherEditText.getText().toString());
        volumesRequest.setPrintType(getPrintType());
        volumesRequest.setCategory(binding.categoryEditText.getText().toString());
        return volumesRequest;
    }

    @SuppressLint("NonConstantResourceId")
    private PrintType getPrintType() {
        @IdRes Integer checkedRadioButtonId = binding.printTypeRadioGroup.getCheckedRadioButtonId();
        if (R.id.booksRadioButton == checkedRadioButtonId) {
            return PrintType.BOOKS;
        }
        if (R.id.magazinesRadioButton == checkedRadioButtonId) {
            return PrintType.MAGAZINES;
        }
        return PrintType.ANY;
    }
}

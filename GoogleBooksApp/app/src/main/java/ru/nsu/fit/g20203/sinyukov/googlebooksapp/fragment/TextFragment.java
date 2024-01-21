package ru.nsu.fit.g20203.sinyukov.googlebooksapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.R;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.SearchRecyclerViewFragmentBinding;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.SearchTextFragmentBinding;

public class TextFragment extends Fragment {

    public static final String TEXT_KEY = "TEXT";

    public TextFragment() {
        super(R.layout.search_text_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SearchTextFragmentBinding binding = SearchTextFragmentBinding.inflate(inflater, container, false);
        final View view = binding.getRoot();

        binding.textView.setText(requireArguments().getString(TEXT_KEY));

        return view;
    }
}

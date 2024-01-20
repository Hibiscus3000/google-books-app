package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListActivityBinding;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListActivityBinding binding = ListActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}
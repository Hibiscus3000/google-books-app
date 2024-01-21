package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListItemBinding;

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.VolumeViewHolder> {


    @NonNull
    @Override
    public DummyAdapter.VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DummyAdapter.VolumeViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DummyAdapter.VolumeViewHolder holder, int position) {
        final Volume volume = new Volume();
        volume.setVolumeInfo();
        volume.getVolumeInfo().setTitle("Валера");
        volume.getVolumeInfo().setAuthors(new String[]{"Dfkthf", "Валера"});
        holder.bind(volume);
    }

    @Override
    public int getItemCount() {
        return 1000;
    }

    public static class VolumeViewHolder extends RecyclerView.ViewHolder {

        private final ListItemBinding binding;

        public VolumeViewHolder(@NonNull ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Volume volume) {
            binding.titleTextView.setText(volume.getVolumeInfo().getTitle());
            binding.authorsTextView.setText(String.join(", ", volume.getVolumeInfo().getAuthors()));
        }
    }
}

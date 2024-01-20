package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListItemBinding;

public class VolumesAdapter extends PagingDataAdapter<Volume, VolumesAdapter.VolumeViewHolder> {
    public VolumesAdapter(@NonNull DiffUtil.ItemCallback<Volume> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VolumeViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeViewHolder holder, int position) {
        final Volume volume = getItem(position);
        holder.bind(volume);
    }

    public static class VolumeViewHolder extends RecyclerView.ViewHolder {

        private final ListItemBinding binding;

        public VolumeViewHolder(@NonNull View view) {
            super(view);
            binding = ListItemBinding.inflate(LayoutInflater.from(view.getContext()));
        }

        public void bind(Volume volume) {
            binding.titleTextView.setText(volume.getVolumeInfo().getTitle());
            binding.authorsTextView.setText(String.join(", ", volume.getVolumeInfo().getAuthors()));
        }
    }
}

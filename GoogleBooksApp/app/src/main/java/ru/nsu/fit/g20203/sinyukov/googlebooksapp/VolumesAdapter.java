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
        return new VolumesAdapter.VolumeViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeViewHolder holder, int position) {
        final Volume volume = getItem(position);
        holder.bind(volume);
    }

    public static class VolumeViewHolder extends RecyclerView.ViewHolder {

        private final ListItemBinding binding;

        public VolumeViewHolder(@NonNull ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Volume volume) {
            final Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            binding.titleTextView.setText(getText(volumeInfo.getTitle()));
            binding.authorsTextView.setText(getText(volumeInfo.getAuthors(), ", "));
        }

        @NonNull
        private String getText(String text) {
            return null != text ? text : "";
        }

        @NonNull
        private String getText(String[] array, String delimiter) {
            return null != array ? String.join(delimiter, array) : "";
        }
    }
}

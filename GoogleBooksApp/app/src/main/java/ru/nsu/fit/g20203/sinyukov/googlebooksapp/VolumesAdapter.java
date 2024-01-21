package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.databinding.ListItemBinding;

public class VolumesAdapter extends PagingDataAdapter<Volume, VolumesAdapter.VolumeViewHolder> {

    private final static int imageSize = 150;

    private final OnVolumeClickListener onVolumeClickListener;

    public VolumesAdapter(@NonNull DiffUtil.ItemCallback<Volume> diffCallback, OnVolumeClickListener onVolumeClickListener) {
        super(diffCallback);
        this.onVolumeClickListener = onVolumeClickListener;
    }

    @NonNull
    @Override
    public VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VolumesAdapter.VolumeViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false), onVolumeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeViewHolder holder, int position) {
        final Volume volume = getItem(position);
        holder.bind(volume);
    }

    public static class VolumeViewHolder extends RecyclerView.ViewHolder {

        private final ListItemBinding binding;
        private final OnVolumeClickListener onVolumeClickListener;

        public VolumeViewHolder(@NonNull ListItemBinding binding, OnVolumeClickListener onVolumeClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onVolumeClickListener = onVolumeClickListener;
        }

        public void bind(Volume volume) {
            final Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

            if (null != volumeInfo) {
                bindImage(volumeInfo);

                binding.titleTextView.setText(getText(volumeInfo.getTitle()));
                binding.authorsTextView.setText(getText(volumeInfo.getAuthors(), ", "));
            }

            binding.getRoot().setOnClickListener(v -> onVolumeClickListener.onVolumeClicked(volume));
        }

        private void bindImage(Volume.VolumeInfo volumeInfo) {
            VolumeImageLoader.loadImage(binding.imageView, volumeInfo.getImageLinks(),
                    imageLinks -> imageLinks.get(), imageSize);
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

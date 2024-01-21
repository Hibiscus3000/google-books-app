package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class VolumeComparator extends DiffUtil.ItemCallback<Volume>{
    @Override
    public boolean areItemsTheSame(@NonNull Volume oldItem, @NonNull Volume newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Volume oldItem, @NonNull Volume newItem) {
        return oldItem.equals(newItem);
    }
}

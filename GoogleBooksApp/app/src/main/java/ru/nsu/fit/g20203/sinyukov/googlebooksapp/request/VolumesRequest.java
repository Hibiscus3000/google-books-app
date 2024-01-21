package ru.nsu.fit.g20203.sinyukov.googlebooksapp.request;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VolumesRequest {

    @NotNull
    private final String title;
    @NotNull
    private final String author;
    @NotNull
    private final String publisher;
    @NotNull
    private final PrintType printType;
    @Nullable
    private final String category;

    public VolumesRequest(@NotNull String title, @NotNull String author, @NotNull String publisher, @NotNull PrintType printType, @Nullable String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.printType = printType;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPrintType() {
        return printType.toString();
    }

    public String getCategory() {
        return category;
    }

    public boolean isValid() {
        return !title.isEmpty() || !author.isEmpty() || !publisher.isEmpty() || !category.isEmpty();
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + title + " Author: " + author + " Publisher: " + publisher + " Print type: " + printType + " Category: " + getCategory();
    }
}

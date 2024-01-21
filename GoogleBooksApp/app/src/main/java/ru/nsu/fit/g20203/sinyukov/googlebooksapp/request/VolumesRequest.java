package ru.nsu.fit.g20203.sinyukov.googlebooksapp.request;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VolumesRequest {

    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String publisher;
    @NotNull
    private PrintType printType;
    @Nullable
    private String category;

    public VolumesRequest() {
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
        return category.getString();
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public void setAuthor(@NotNull String author) {
        this.author = author;
    }

    public void setPublisher(@NotNull String publisher) {
        this.publisher = publisher;
    }

    public void setPrintType(@NotNull PrintType printType) {
        this.printType = printType;
    }

    public void setCategory(@NotNull String category) {
        this.category = category;
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

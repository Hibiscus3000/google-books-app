package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.category.Category;

public abstract class BooksRequest {

    @NotNull
    private final String title;
    @NotNull
    private final String author;
    @NotNull
    private final String publisher;
    @NotNull
    private final PrintType printType;
    @Nullable
    private final Category category;

    public BooksRequest(String title, String author, String publisher, PrintType printType, Category category) {
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
        return category.getString();
    }

    public boolean isValid() {
        return !title.isEmpty() || !author.isEmpty() || !publisher.isEmpty() || null != category;
    }
}

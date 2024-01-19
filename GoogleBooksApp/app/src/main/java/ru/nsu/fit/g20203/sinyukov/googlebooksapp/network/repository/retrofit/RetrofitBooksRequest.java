package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository.retrofit;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.BooksRequest;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.PrintType;
import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.category.Category;

public class RetrofitBooksRequest extends BooksRequest {

    private final int startIndex;
    private final int maxResults;

    public RetrofitBooksRequest(String title,
                                String author,
                                String publisher,
                                PrintType printType,
                                Category category,
                                int startIndex,
                                int maxResults) {
        super(title, author, publisher, printType, category);
        this.startIndex = startIndex;
        this.maxResults = maxResults;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getMaxResults() {
        return maxResults;
    }
}

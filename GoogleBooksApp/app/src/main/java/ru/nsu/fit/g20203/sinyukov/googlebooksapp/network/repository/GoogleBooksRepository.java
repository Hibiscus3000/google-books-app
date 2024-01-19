package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.repository;

import ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.BooksRequest;

public interface GoogleBooksRepository<R extends BooksRequest,
        C extends GoogleBooksRepositoryCallback<?, ?>> {

    void listBooks(R booksRequest, C callback);

}

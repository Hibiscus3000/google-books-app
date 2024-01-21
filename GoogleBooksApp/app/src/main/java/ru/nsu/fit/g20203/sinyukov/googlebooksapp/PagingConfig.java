package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

public class PagingConfig {

    public static final int PAGE_SIZE = 10;
    public static final int PREFETCH_DISTANCE = 3 * PAGE_SIZE;
    public static final int INITIAL_LOAD_SIZE = 3 * PAGE_SIZE;
    public static final int MAX_SIZE = 10 * PAGE_SIZE;

    public static androidx.paging.PagingConfig getPagingConfig() {
        return new androidx.paging.PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, false, INITIAL_LOAD_SIZE, MAX_SIZE);
    }
}

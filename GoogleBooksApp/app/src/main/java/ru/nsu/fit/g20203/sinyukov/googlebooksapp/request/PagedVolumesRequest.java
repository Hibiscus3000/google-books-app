package ru.nsu.fit.g20203.sinyukov.googlebooksapp.request;

import static ru.nsu.fit.g20203.sinyukov.googlebooksapp.PagingConfig.PAGE_SIZE;

import androidx.annotation.NonNull;

public class PagedVolumesRequest {

    private static final int MAX_RESULTS_THRESHOLD = 40;

    private final VolumesRequest volumesRequest;
    private final int pageNumber;
    private final int startIndex;
    private final int maxResults;

    public PagedVolumesRequest(VolumesRequest volumesRequest,
                               int pageNumber, int maxResults) {
        this.volumesRequest = volumesRequest;
        this.pageNumber = pageNumber;
        this.startIndex = (pageNumber - 1) * PAGE_SIZE;
        this.maxResults = Math.min(MAX_RESULTS_THRESHOLD, maxResults);
    }

    public VolumesRequest getVolumesRequest() {
        return volumesRequest;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    @NonNull
    @Override
    public String toString() {
        return "Page Number: " + pageNumber + " Start index: " + startIndex + " Max results: " + maxResults;
    }
}

package ru.nsu.fit.g20203.sinyukov.googlebooksapp.request;

import static ru.nsu.fit.g20203.sinyukov.googlebooksapp.PagingConfig.PAGE_SIZE;

public class PagedVolumesRequest {

    private final VolumesRequest volumesRequest;
    private final int pageNumber;
    private final int startIndex;

    public PagedVolumesRequest(VolumesRequest volumesRequest,
                               int pageNumber) {
        this.volumesRequest = volumesRequest;
        this.pageNumber = pageNumber;
        this.startIndex = (pageNumber - 1) * PAGE_SIZE;
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
        return PAGE_SIZE;
    }
}

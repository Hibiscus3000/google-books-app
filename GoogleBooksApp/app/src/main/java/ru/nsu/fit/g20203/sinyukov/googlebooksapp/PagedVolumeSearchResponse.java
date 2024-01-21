package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import java.util.Arrays;
import java.util.List;

public class PagedVolumeSearchResponse {

    private final List<Volume> volumes;
    private final Integer nextPageNumber;
    private final Integer prevPageNumber;

    public PagedVolumeSearchResponse(Volume[] volumes, int currentPageNumber) {
        this.volumes = Arrays.asList(volumes);
        this.nextPageNumber = 0 != volumes.length ? currentPageNumber + 1 : null;
        this.prevPageNumber = 1 != currentPageNumber ? currentPageNumber - 1 : null;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public Integer getNextPageNumber() {
        return nextPageNumber;
    }

    public Integer getPrevPageNumber() {
        return prevPageNumber;
    }
}

package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import java.util.List;

public class PagedVolumeSearchResponse {

    private final List<Volume> volumes;
    private final int nextPageNumber;

    public PagedVolumeSearchResponse(List<Volume> volumes, int nextPageNumber) {
        this.volumes = volumes;
        this.nextPageNumber = nextPageNumber;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public int getNextPageNumber() {
        return nextPageNumber;
    }
}

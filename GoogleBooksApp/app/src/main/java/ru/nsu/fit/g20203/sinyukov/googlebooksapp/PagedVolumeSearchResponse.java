package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import java.util.Arrays;
import java.util.List;

public class PagedVolumeSearchResponse {

    private final List<Volume> volumes;
    private final Integer nextPageNumber;

    public PagedVolumeSearchResponse(Volume[] volumes, int nextPageNumber) {
        this.volumes = Arrays.asList(volumes);
        this.nextPageNumber = nextPageNumber;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public Integer getNextPageNumber() {
        return nextPageNumber;
    }
}

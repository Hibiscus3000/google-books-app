package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.category;

public enum PredefinedCategory implements Category {
    BUSINESS_ECONOMICS_ENTREPRENEURSHIP("Business & Economics / Entrepreneurship");

    private final String stringRepresentation;

    PredefinedCategory(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String getString() {
        return stringRepresentation;
    }
}

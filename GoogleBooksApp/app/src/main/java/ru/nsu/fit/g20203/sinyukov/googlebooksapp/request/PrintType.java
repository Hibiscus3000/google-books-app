package ru.nsu.fit.g20203.sinyukov.googlebooksapp.request;

public enum PrintType {
    ALL("all"), BOOKS("books"), MAGAZINES("magazines");

    private final String stringRepresentation;

    PrintType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}

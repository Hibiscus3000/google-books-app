package ru.nsu.fit.g20203.sinyukov.googlebooksapp.network.request.category;

public class CustomCategory implements Category{

    private final String value;

    public CustomCategory(String value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return value;
    }
}

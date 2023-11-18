package ru.otus.exception;

public class GenreNotFoundException extends RuntimeException {

    private static final String MSG = "Genre not found";

    public GenreNotFoundException() {
        super(MSG);
    }

}

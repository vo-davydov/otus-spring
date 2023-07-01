package ru.otus.exception;

public class AmbiguousAuthorException extends RuntimeException {

    private static final String MSG = "Found two or more authors, please write author id";

    public AmbiguousAuthorException() {
        super(MSG);
    }
}

package ru.otus.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static final String MSG = "Author not found";

    public AuthorNotFoundException() {
        super(MSG);
    }

}

package ru.otus.exception;

public class BookNotFoundException extends RuntimeException {

    private static final String MSG = "Book not found";

    public BookNotFoundException() {
        super(MSG);
    }

}

package ru.otus.exception;

public class CommentNotFoundException extends RuntimeException {

    private static final String MSG = "Comment not found";

    public CommentNotFoundException() {
        super(MSG);
    }

}

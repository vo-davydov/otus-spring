package ru.otus.exception;

public class FileCannotBeReadException  extends RuntimeException {

    private static final String MSG = "File %s cannot be read";

    public FileCannotBeReadException(String fileName, Throwable cause) {
        super(String.format(MSG, fileName), cause);
    }

}

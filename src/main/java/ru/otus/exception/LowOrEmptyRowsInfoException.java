package ru.otus.exception;

public class LowOrEmptyRowsInfoException extends RuntimeException {

    private static final String MSG = "Rows are empty or don't have enough information";

    public LowOrEmptyRowsInfoException() {
        super(MSG);
    }

}

package ru.otus.core;

public interface MessageSourceHelper {

    String getMessage(String code);

    String getMessage(String code, Object...args);

}

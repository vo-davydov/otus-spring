package ru.otus.service;

import java.util.List;

public interface InputService {

    int readInt();

    int readIntWithPrompt(String prompt);

    String readString();

    String readStringWithPrompt(String prompt);

    List<String> readAll();

}

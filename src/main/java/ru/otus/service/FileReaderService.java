package ru.otus.service;

import java.util.List;

public interface FileReaderService {

    List<String> getRowsByFileName(String fileName);
}

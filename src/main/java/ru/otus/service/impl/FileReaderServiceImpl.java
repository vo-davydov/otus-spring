package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.exception.FileCannotBeReadException;
import ru.otus.service.FileReaderService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public List<String> getRowsByFileName(String fileName) {
        var resource = getURL(fileName);

        try (var input = new Scanner(resource.openStream())) {
            List<String> lines = new ArrayList<>();
            while (input.hasNext()) {
                lines.add(input.nextLine());
            }
            return lines;
        } catch (IOException e) {
            throw new FileCannotBeReadException(fileName, e);
        }

    }

    private static URL getURL(String fileName) {
        return ClassLoader.getSystemResource(fileName);
    }
}

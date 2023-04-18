package ru.otus.dao.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static List<String> getRowsByFileName(String fileName) {
        var resource = getURL(fileName);

        try (var input = new Scanner(resource.openStream())) {
            List<String> lines = new ArrayList<>();
            while (input.hasNext()) {
                lines.add(input.nextLine());
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static URL getURL(String fileName) {
        return ClassLoader.getSystemResource(fileName);
    }
}

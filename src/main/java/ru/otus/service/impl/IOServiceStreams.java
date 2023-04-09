package ru.otus.service.impl;

import ru.otus.service.IOService;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOServiceStreams implements IOService {

    private final PrintStream output;

    private final Scanner input;

    public IOServiceStreams(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    public IOServiceStreams(String fileName) {
        var resource = ClassLoader.getSystemResource(fileName);
        try {
            output = new PrintStream(fileName);
            input = new Scanner(resource.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public int readIntWithPrompt(String prompt) {
        outputString(prompt);
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public String readString() {
        return input.nextLine();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        outputString(prompt);
        return input.nextLine();
    }

    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        while (input.hasNext()) {
            lines.add(input.nextLine());
        }
        return lines;
    }
}

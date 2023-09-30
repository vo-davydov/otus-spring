package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.service.OutputService;

import java.io.PrintStream;

@Service
@AllArgsConstructor
public class OutputServiceImpl implements OutputService {

    private final PrintStream output;

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public void outputString(String s, Object... args) {
        output.println(String.format(s, args));
    }
}

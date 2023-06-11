package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.GameService;

@ShellComponent
@RequiredArgsConstructor
public class GameServiceShell {

    private final GameService gameService;

    @ShellMethod(value = "Start game command", key = {"s", "start"})
    public void start() {
        gameService.start();
    }

}

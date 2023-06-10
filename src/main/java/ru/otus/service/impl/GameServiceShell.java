package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.GameService;

@ShellComponent
@RequiredArgsConstructor
public class GameServiceShell implements GameService {

    @Qualifier("gameServiceImpl")
    private final GameService gameService;

    @ShellMethod(value = "Start game command", key = {"s", "start"})
    @Override
    public void start() {
        gameService.start();
    }

}

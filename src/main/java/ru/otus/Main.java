package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.configuration.CoreConfiguration;
import ru.otus.service.GameService;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(CoreConfiguration.class);
        var game = context.getBean("gameService", GameService.class);
        game.start();
    }
}
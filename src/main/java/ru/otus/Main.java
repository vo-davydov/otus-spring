package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.GameService;

public class Main {
    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var game = context.getBean("gameService", GameService.class);
        game.start();
    }
}
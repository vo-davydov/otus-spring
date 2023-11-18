package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class Main {
    //todo нахрен миграцию на java или на mogock, надо сделать миграцию на java script
    //todo разобраться с Spring WebFlux
    //todo Необходимо использовать Reactive Spring Data Repositories.
    //todo В качестве БД лучше использовать MongoDB или Redis. Использовать PostgreSQL и экспериментальную R2DBC не рекомендуется.
    //todo RxJava vs Project Reactor - на Ваш вкус.
    //todo Вместо классического Spring MVC и embedded Web-сервера использовать WebFlux.
    //todo Опционально: реализовать на Functional Endpoints
    //todo тесты на controller, на service?
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}
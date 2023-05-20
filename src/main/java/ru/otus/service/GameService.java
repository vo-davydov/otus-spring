package ru.otus.service;

import ru.otus.domain.User;

public interface GameService {

    void start();

    void printScore(User user, int score);

}

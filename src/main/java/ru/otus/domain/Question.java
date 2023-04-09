package ru.otus.domain;

import java.util.List;

public record Question(String question, List<Answer> answers) {

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}

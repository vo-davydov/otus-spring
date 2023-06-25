package ru.otus.domain;

import java.util.List;

public record Question(String question, List<Answer> answers) {

    public boolean isCorrectAnswer(int answer) {
        var an = answers.get(answer);
        return an != null && an.isCorrect();
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }

}

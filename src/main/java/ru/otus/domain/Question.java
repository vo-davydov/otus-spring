package ru.otus.domain;

import java.util.List;

public class Question {
    private final String question;

    private final List<Answer> answers;

    public Question(String question, List<Answer> answers) {
        this.answers = answers;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}

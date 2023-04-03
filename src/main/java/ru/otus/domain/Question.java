package ru.otus.domain;

import java.util.Arrays;

public class Question {
    private final String question;

    private final String[] answers;

    private final int correctAnswer;

    public Question(String question, String[] answers, int correctAnswer) {
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.question = question;
    }

    public boolean isAnswerCorrect(int answer) {
        return correctAnswer == answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + Arrays.toString(answers) +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}

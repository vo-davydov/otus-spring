package ru.otus.dao.impl;

import org.springframework.lang.NonNull;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.exception.LowOrEmptyRowsInfoException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class QuestionDaoCSV implements QuestionDao {

    private final static Logger LOGGER = Logger.getLogger(QuestionDaoCSV.class.getName());

    private final String csv;

    public QuestionDaoCSV(String csv) {
        this.csv = csv;
    }

    @Override
    public List<Question> getAll() {
        var resource = ClassLoader.getSystemResource(csv);
        return getAllQuestionsByResource(resource);
    }

    private List<Question> getAllQuestionsByResource(@NonNull URL url) {
        List<Question> questions = new ArrayList<>();
        try (var scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                LOGGER.info(String.format("Reading line: %s", line));
                var rows = line.split(";");
                var answer = creatQuestionByRows(rows);
                questions.add(answer);
            }
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Question creatQuestionByRows(String[] rows) {

        if (rows == null || rows.length < 3) {
            throw new LowOrEmptyRowsInfoException();
        }

        String question = rows[0];
        int correctAnswer = Integer.parseInt(rows[rows.length - 1]);
        String[] answers = Arrays.copyOfRange(rows, 1, rows.length - 1);
        List<Answer> answerList = new ArrayList<>();
        for (int i = 0; i < answers.length; i++) {
            var text = answers[i];
            Answer a = i == correctAnswer ? new Answer(text, true) : new Answer(text);
            answerList.add(a);
        }
        return new Question(question, answerList);
    }


}

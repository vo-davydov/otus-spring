package ru.otus.dao.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.dao.util.FileReader;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.exception.LowOrEmptyRowsInfoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class QuestionDaoCSV implements QuestionDao {

    private final static Logger LOGGER = Logger.getLogger(QuestionDaoCSV.class.getName());

    private final String CSV;

    public QuestionDaoCSV(String CSV) {
        this.CSV = CSV;
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();

        var resource = FileReader.getRowsByFileName(CSV);
        for (var line : resource) {
            LOGGER.info(String.format("Reading line: %s", line));
            var rows = line.split(";");
            var question = creatQuestionByRows(rows);
            questions.add(question);
        }

        return questions;
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

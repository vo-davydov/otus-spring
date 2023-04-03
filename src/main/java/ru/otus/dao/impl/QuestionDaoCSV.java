package ru.otus.dao.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.exception.LowOrEmptyRowsInfoException;
import ru.otus.service.FileReaderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class QuestionDaoCSV implements QuestionDao {

    private final static Logger LOGGER = Logger.getLogger(QuestionDaoCSV.class.getName());

    private final String csv;

    private final FileReaderService fileReaderService;

    public QuestionDaoCSV(String csv, FileReaderService fileReaderService) {
        this.csv = csv;
        this.fileReaderService = fileReaderService;
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();

        var resource = getRowsByFile(csv);
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

    private List<String> getRowsByFile(String fileName) {
        return fileReaderService.getRowsByFileName(csv);
    }
}

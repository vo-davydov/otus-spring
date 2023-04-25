package ru.otus.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.exception.LowOrEmptyRowsInfoException;
import ru.otus.util.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class QuestionDaoCSV implements QuestionDao {

    private final static Logger LOGGER = Logger.getLogger(QuestionDaoCSV.class.getName());

    private final String fileName;

    private final FileReader fileReader;

    public QuestionDaoCSV(String fileName, FileReader fileReader) {
        this.fileName = fileName;
        this.fileReader = fileReader;
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();
        var rowsFromCSV = getRowsFromCsv();

        for (var line : rowsFromCSV) {
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

    private List<String> getRowsFromCsv() {
        return fileReader.getRowsByFileName(fileName);
    }
}
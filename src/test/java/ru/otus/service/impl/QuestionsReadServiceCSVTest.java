package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoCSV;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionsReadServiceCSVTest {

    private QuestionDao questionDao;

    private final static String CSV = "questions.csv";

    @BeforeEach
    public void init() {
        questionDao = new QuestionDaoCSV(CSV);
    }

    @Test
    void fileShouldHaveRightSize() {
        var questions = questionDao.getAll();
        assertEquals(5, questions.size());
    }
}
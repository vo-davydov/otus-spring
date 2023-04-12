package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoCSV;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Configuration
@TestPropertySource("classpath:application.properties")
class QuestionsDaoCSVTest {

    private QuestionDao questionDao;

    @Value("${question.file-name}")
    private String csv;

    @BeforeEach
    public void init() {
        questionDao = new QuestionDaoCSV(csv);
    }

    @Test
    void fileShouldHaveRightSize() {
        var questions = questionDao.getAll();
        assertEquals(5, questions.size());
    }
}
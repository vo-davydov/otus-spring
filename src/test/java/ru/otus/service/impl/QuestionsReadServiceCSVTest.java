package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.DefaultResourceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionsReadServiceCSVTest {

    private final static DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    private static QuestionsReadServiceCSV questionsReadServiceCSV;

    @BeforeAll
    public static void init() {
        questionsReadServiceCSV = new QuestionsReadServiceCSV();
    }

    @ParameterizedTest
    @ValueSource(strings = {"questions.csv"})
    void fileShouldHaveRightSize(String fileName) {
        var resource = defaultResourceLoader.getResource(fileName);
        var questions = questionsReadServiceCSV.getAllQuestions(resource);
        assertEquals(5, questions.size());
    }
}
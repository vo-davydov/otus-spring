package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoCSV;
import ru.otus.util.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionsDaoCSVTest {

    private QuestionDao questionDao;

    private final static String FIRST_QUESTION = "What Is a Spring Bean?";

    private final static String SECOND_QUESTION = "Are Singleton Beans Thread-Safe?";

    private final static String THIRD_QUESTION = "What Is the Spring Java-Based Configuration?";

    private final static String FOURTHS_QUESTION = "Can We Have Multiple Spring Configuration Files in One Project?";

    private final static String FIFTH_QUESTION = "How Does the @RequestMapping Annotation Work?";

    private final static String CSV = "questions_en.csv";

    @BeforeEach
    public void init() {
        var fileReader = new FileReader();
        questionDao = new QuestionDaoCSV(CSV, fileReader);
    }

    @Test
    void fileShouldHaveRightSize() {
        var questions = questionDao.getAll();
        assertEquals(5, questions.size());
    }

    @Test
    void readQuestions() {
        var questions = questionDao.getAll();

        var question1 = questions.get(0);
        assertNotNull(question1);
        assertEquals(question1.question(), FIRST_QUESTION);

        var question2 = questions.get(1);
        assertNotNull(question2);
        assertEquals(question2.question(), SECOND_QUESTION);

        var question3 = questions.get(2);
        assertNotNull(question3);
        assertEquals(question3.question(), THIRD_QUESTION);

        var question4 = questions.get(3);
        assertNotNull(question4);
        assertEquals(question4.question(), FOURTHS_QUESTION);

        var question5 = questions.get(4);
        assertNotNull(question5);
        assertEquals(question5.question(), FIFTH_QUESTION);

    }
}

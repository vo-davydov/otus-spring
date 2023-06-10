package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.core.MessageSourceHelper;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.otus.core.GameMessages.*;

@SpringBootTest
public class GameServiceImplTest {
    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @Mock
    private MessageSourceHelper messageSourceHelper;

    private GameService gameService;

    @BeforeEach
    void init() {
        gameService = new GameServiceImpl(questionDao, ioService, messageSourceHelper);
    }

    @Test
    void start() {
        var questions = new ArrayList<>();
        var answer1 = new Answer("true", true);
        var answer2 = new Answer("false", false);
        var question1 = new Question("First test question?", Arrays.asList(answer1, answer2));
        var question2 = new Question("Second test question?", Arrays.asList(answer1, answer2));
        questions.add(question1);
        questions.add(question2);

        when(questionDao.getAll()).then((invocation) -> questions);

        when(ioService.readIntWithPrompt(any(String.class))).then((invocation -> 0));

        when(messageSourceHelper.getMessage(GREETING_CODE)).thenReturn(GREETING_CODE);
        when(messageSourceHelper.getMessage(ASK_NAME_CODE)).thenReturn(ASK_NAME_CODE);
        when(messageSourceHelper.getMessage(ASK_ANSWER_CODE)).thenReturn(ASK_ANSWER_CODE);
        when(messageSourceHelper.getMessage(ASK_QUESTION_CODE, 1 , question1.question())).thenReturn(ASK_QUESTION_CODE);
        when(messageSourceHelper.getMessage(ASK_QUESTION_CODE, 2 , question2.question())).thenReturn(ASK_QUESTION_CODE);
        when(messageSourceHelper.getMessage(ASK_LASTNAME_CODE)).thenReturn(ASK_LASTNAME_CODE);
        when(messageSourceHelper.getMessage(SCORE_CODE, null, 2)).thenReturn(SCORE_CODE);
        gameService.start();

        verify(ioService).outputString(GREETING_CODE);
        verify(ioService, Mockito.times(2)).readIntWithPrompt(ASK_ANSWER_CODE);
        verify(ioService).readStringWithPrompt(ASK_NAME_CODE);
        verify(ioService).readStringWithPrompt(ASK_LASTNAME_CODE);
        verify(ioService).outputString(SCORE_CODE);
    }
}

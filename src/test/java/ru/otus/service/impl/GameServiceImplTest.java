package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static ru.otus.service.impl.GameServiceImpl.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    private GameService gameService;

    @BeforeEach
    void init() {
        gameService = new GameServiceImpl(questionDao, ioService);
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

        gameService.start();

        verify(ioService).outputString(GREETING_MESSAGE);
        verify(ioService, Mockito.times(2)).readIntWithPrompt(ASK_ANSWER);
        verify(ioService).readStringWithPrompt(ASK_NAME_MESSAGE);
        verify(ioService).readStringWithPrompt(ASK_LASTNAME_MESSAGE);
        verify(ioService).outputString(String.format(SCORE_MSG, null, 2));
    }
}
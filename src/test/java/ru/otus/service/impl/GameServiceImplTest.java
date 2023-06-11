package ru.otus.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.core.MessageSourceHelper;
import ru.otus.dao.QuestionDao;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.otus.core.GameMessages.*;

@SpringBootTest
public class GameServiceImplTest {

    private GameService gameService;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private MessageSourceHelper messageSourceHelper;

    @Mock
    private IOService ioService;

    @BeforeEach
    void init() {
        gameService = new GameServiceImpl(questionDao, ioService, messageSourceHelper);
    }

    @Test
    void start() {
        var questions = questionDao.getAll();
        var hasQuestions = questions.size() > 0;
        Assertions.assertTrue(hasQuestions);

        when(ioService.readIntWithPrompt(any(String.class))).then((invocation -> 0));

        gameService.start();

        verify(ioService).outputString(messageSourceHelper.getMessage(GREETING_CODE));
        verify(ioService, Mockito.times(5)).readIntWithPrompt(messageSourceHelper.getMessage(ASK_ANSWER_CODE));
        verify(ioService).readStringWithPrompt(messageSourceHelper.getMessage(ASK_NAME_CODE));
        verify(ioService).readStringWithPrompt(messageSourceHelper.getMessage(ASK_LASTNAME_CODE));
    }
}

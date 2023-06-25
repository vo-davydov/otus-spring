package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.core.MessageSourceHelper;
import ru.otus.dao.QuestionDao;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.otus.core.GameMessages.*;

@SpringBootTest
public class GameServiceImplTest {

    @Autowired
    private GameService gameService;

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private MessageSourceHelper messageSourceHelper;

    @MockBean
    private IOService ioService;

    @Test
    void start() {
        when(questionDao.getAll()).thenReturn(new ArrayList<>());
        when(messageSourceHelper.getMessage(GREETING_CODE)).thenReturn(GREETING_CODE);
        when(messageSourceHelper.getMessage(ASK_ANSWER_CODE)).thenReturn(ASK_ANSWER_CODE);
        when(messageSourceHelper.getMessage(ASK_NAME_CODE)).thenReturn(ASK_NAME_CODE);
        when(messageSourceHelper.getMessage(ASK_LASTNAME_CODE)).thenReturn(ASK_LASTNAME_CODE);
        when(ioService.readIntWithPrompt(any(String.class))).then((invocation -> 0));

        gameService.start();

        verify(ioService, Mockito.times(1)).outputString(GREETING_CODE);
        verify(ioService, Mockito.times(0)).readIntWithPrompt(ASK_ANSWER_CODE);
        verify(ioService, Mockito.times(1)).readStringWithPrompt(ASK_NAME_CODE);
        verify(ioService, Mockito.times(1)).readStringWithPrompt(ASK_LASTNAME_CODE);
    }
}

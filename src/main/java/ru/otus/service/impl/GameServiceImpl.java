package ru.otus.service.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final QuestionDao questionDao;

    private final IOService ioService;

    public GameServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public void start() {
        var questions = questionDao.getAll();

        LOGGER.info(String.format("Questions: %s", questions));

        for (int i = 0; i < questions.size(); i++) {
            int questionNumber = i + 1;
            var question = questions.get(i);
            var msg = String.format("%s questions is: %s", questionNumber, question.question());
            ioService.outputString(msg);
            printAnswers(question);
        }
    }

    private void printAnswers(Question question) {
        var answers = question.answers();
        answers.forEach(a -> ioService.outputString(a.getText()));
    }
}

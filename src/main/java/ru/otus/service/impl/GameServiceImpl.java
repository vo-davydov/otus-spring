package ru.otus.service.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.service.GameService;

import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final QuestionDao questionDao;

    private final IOServiceStreams iOServiceStreams;

    public GameServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
        this.iOServiceStreams = new IOServiceStreams(System.out, System.in);
    }

    @Override
    public void start() {
        var questions = questionDao.getAll();

        LOGGER.info(String.format("Questions: %s", questions));

        for (int i = 0; i < questions.size(); i++) {
            int questionNumber = i + 1;
            var question = questions.get(i);
            var msg = String.format("%s questions is: %s", questionNumber, question.question());
            iOServiceStreams.outputString(msg);
            printAnswers(question);
        }
    }

    private void printAnswers(Question question) {
        var answers = question.answers();
        answers.forEach(a -> iOServiceStreams.outputString(a.getText()));
    }
}

package ru.otus.service.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.service.GameService;

import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final QuestionDao QuestionDao;

    public GameServiceImpl(QuestionDao QuestionDao) {
        this.QuestionDao = QuestionDao;
    }

    @Override
    public void start() {
        var questions = QuestionDao.getAll();

        LOGGER.info(String.format("Questions: %s", questions));

        for (int i = 0; i < questions.size(); i++) {
            int questionNumber = i + 1;
            var question = questions.get(i);
            System.out.printf("%s questions is: %s%n", questionNumber, question.getQuestion());
            printAnswers(question);
        }

    }

    private void printAnswers(Question question) {
        var answers = question.getAnswers();
        answers.forEach(a -> System.out.println(a.getText()));
    }
}

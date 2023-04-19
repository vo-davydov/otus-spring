package ru.otus.service.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.domain.User;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    public final static String GREETING_MESSAGE = "Hello";

    public final static String ASK_NAME_MESSAGE = "Please write your name";

    public final static String ASK_LASTNAME_MESSAGE = "Please write your lastname";

    public final static String ASK_ANSWER = "Please choose the correct answer by writing number from 0 to 4";

    public final static String SCORE_MSG = "%s score is %s";

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final QuestionDao questionDao;

    private final IOService ioService;

    public GameServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public void start() {
        ioService.outputString(GREETING_MESSAGE);
        var name = askName();
        var lastName = askLastName();
        var user = new User(name, lastName);
        var score = 0;
        var questions = questionDao.getAll();

        LOGGER.info(String.format("Questions: %s", questions));

        for (int i = 0; i < questions.size(); i++) {
            int questionNumber = i + 1;
            var question = questions.get(i);
            var msg = String.format("%s questions is: %s", questionNumber, question.question());
            ioService.outputString(msg);
            printAnswers(question);

            var userAnswer = askAnswer();
            if (question.isCorrectAnswer(userAnswer)) {
                score++;
            }
        }

        printScore(user, score);
    }

    private String askName() {
        return ioService.readStringWithPrompt(ASK_NAME_MESSAGE);
    }

    private String askLastName() {
        return ioService.readStringWithPrompt(ASK_LASTNAME_MESSAGE);
    }

    private int askAnswer() {
        return ioService.readIntWithPrompt(ASK_ANSWER);
    }

    private void printAnswers(Question question) {
        var answers = question.answers();
        answers.forEach(a -> ioService.outputString(a.getText()));
    }

    public void printScore(User user, int score) {
        ioService.outputString(String.format(SCORE_MSG, user.name(), score));
    }

}

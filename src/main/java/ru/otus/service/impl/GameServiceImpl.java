package ru.otus.service.impl;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.domain.User;
import ru.otus.service.GameService;

import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final static String GREETING_MESSAGE = "Hello";

    private final static String ASK_NAME_MESSAGE = "Please write your name";

    private final static String ASK_LASTNAME_MESSAGE = "Please write your lastname";

    private final static String ASK_ANSWER = "Please choose the correct answer by writing number from 0 to 4";

    private final static String SCORE_MSG = "%s score is %s";

    private final QuestionDao questionDao;

    private final IOServiceStreams iOServiceStreams;

    public GameServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
        this.iOServiceStreams = new IOServiceStreams(System.out, System.in);
    }

    @Override
    public void start() {
        iOServiceStreams.outputString(GREETING_MESSAGE);
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
            iOServiceStreams.outputString(msg);
            printAnswers(question);

            var userAnswer = askAnswer();
            if (question.isCorrectAnswer(userAnswer)) {
                score++;
            }
        }

        printScore(user, score);
    }

    private String askName() {
        return iOServiceStreams.readStringWithPrompt(ASK_NAME_MESSAGE);
    }

    private String askLastName() {
        return iOServiceStreams.readStringWithPrompt(ASK_LASTNAME_MESSAGE);
    }

    private int askAnswer() {
        return iOServiceStreams.readIntWithPrompt(ASK_ANSWER);
    }

    private void printAnswers(Question question) {
        var answers = question.answers();
        answers.forEach(a -> iOServiceStreams.outputString(a.getText()));
    }

    private void printScore(User user, int score) {
        iOServiceStreams.outputString(String.format(SCORE_MSG, user.name(), score));
    }

}

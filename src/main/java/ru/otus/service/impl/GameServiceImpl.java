package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.domain.User;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import java.util.List;
import java.util.logging.Logger;

@Service
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
        var user = getUser();
        LOGGER.info(String.format("User: %s", user));

        var questions = questionDao.getAll();
        LOGGER.info(String.format("Questions: %s", questions));

        var score = getScore(questions);
        LOGGER.info(String.format("Score is: %s", score));

        printScore(user, score);
    }

    private User getUser() {
        var name = askName();
        var lastName = askLastName();
        return new User(name, lastName);
    }

    private String askName() {
        return ioService.readStringWithPrompt(ASK_NAME_MESSAGE);
    }

    private String askLastName() {
        return ioService.readStringWithPrompt(ASK_LASTNAME_MESSAGE);
    }

    private int getScore(List<Question> questions) {
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            int questionNumber = i + 1;
            var question = questions.get(i);
            askQuestion(questionNumber, question);
            var userAnswer = askAnswer();
            if (question.isCorrectAnswer(userAnswer)) {
                score++;
            }
        }

        return score;
    }

    private void askQuestion(int questionNumber, Question question) {
        var msg = String.format("%s questions is: %s", questionNumber, question.question());
        ioService.outputString(msg);
        printAnswers(question);
    }

    private void printAnswers(Question question) {
        var answers = question.answers();
        answers.forEach(a -> ioService.outputString(a.getText()));
    }

    private int askAnswer() {
        return ioService.readIntWithPrompt(ASK_ANSWER);
    }

    public void printScore(User user, int score) {
        ioService.outputString(String.format(SCORE_MSG, user.name(), score));
    }

}

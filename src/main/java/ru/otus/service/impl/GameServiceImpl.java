package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.core.MessageSourceHelper;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.domain.User;
import ru.otus.service.GameService;
import ru.otus.service.IOService;

import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    public final static String GREETING_CODE = "game.greeting";

    public final static String ASK_NAME_CODE = "game.ask.name";

    public final static String ASK_LASTNAME_CODE = "game.ask.lastname";

    public final static String ASK_ANSWER_CODE = "game.ask.answer";

    public final static String SCORE_CODE = "game.score";

    public final static String ASK_QUESTION_CODE = "game.ask.question";

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final QuestionDao questionDao;

    private final IOService ioService;

    private final MessageSourceHelper messageSourceHelper;

    @Override
    public void start() {
        ioService.outputString(messageSourceHelper.getMessage(GREETING_CODE));
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
        return ioService.readStringWithPrompt(messageSourceHelper.getMessage(ASK_NAME_CODE));
    }

    private String askLastName() {
        return ioService.readStringWithPrompt(messageSourceHelper.getMessage(ASK_LASTNAME_CODE));
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
        var msg = messageSourceHelper.getMessage(ASK_QUESTION_CODE, questionNumber, question.question());
        ioService.outputString(msg);
        printAnswers(question);
    }

    private void printAnswers(Question question) {
        var answers = question.answers();
        answers.forEach(a -> ioService.outputString(a.getText()));
    }

    private int askAnswer() {
        return ioService.readIntWithPrompt(messageSourceHelper.getMessage(ASK_ANSWER_CODE));
    }

    public void printScore(User user, int score) {
        ioService.outputString(messageSourceHelper.getMessage(SCORE_CODE, user.name(), score));
    }

}

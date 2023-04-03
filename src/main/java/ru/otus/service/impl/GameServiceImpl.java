package ru.otus.service.impl;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import ru.otus.domain.Question;
import ru.otus.service.GameService;
import ru.otus.service.QuestionsReadService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private final static Logger LOGGER = Logger.getLogger(GameServiceImpl.class.getName());

    private final static String GREETING_MESSAGE = "Hello. Choose the correct answer by number 0, 1, 2 or 3";

    private final QuestionsReadService questionsReadService;

    private final DefaultResourceLoader defaultResourceLoader;

    private String questionResource;

    public GameServiceImpl(QuestionsReadService questionsReadService, DefaultResourceLoader defaultResourceLoader) {
        this.questionsReadService = questionsReadService;
        this.defaultResourceLoader = defaultResourceLoader;
    }

    @Override
    public void start() {
        var thread = new Thread(this::startGame);
        thread.start();
    }

    private List<Question> getQuestions() {
        Resource resource = defaultResourceLoader.getResource(questionResource);
        var result = questionsReadService.getAllQuestions(resource);
        LOGGER.info("Questions: " + result);
        return result;
    }

    public void setQuestionResource(String questionResource) {
        this.questionResource = questionResource;
    }

    private void startGame() {
        var questions = getQuestions();

        System.out.println(GREETING_MESSAGE);
        long correctAnswer = 0L;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            for (int i = 0; i < questions.size(); i++) {
                int questionNumber = i + 1;
                var question = questions.get(i);
                System.out.printf("%s questions is: %s%n", questionNumber, question.getQuestion());
                printAnswers(question);
                var answer = Integer.parseInt(reader.readLine());
                if (question.isAnswerCorrect((answer))) {
                    correctAnswer++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("%s correct answers given%n", correctAnswer);
    }

    private void printAnswers(Question question) {
        var answers = question.getAnswers();
        Arrays.stream(answers).forEach(System.out::println);
    }
}

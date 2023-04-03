package ru.otus.service.impl;

import org.springframework.core.io.Resource;
import ru.otus.domain.Question;
import ru.otus.service.QuestionsReadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class QuestionsReadServiceCSV implements QuestionsReadService {

    @Override
    public List<Question> getAllQuestions(Resource resource) {
        List<Question> questions = new ArrayList<>();
        try {
            Path path = Paths.get(resource.getURI());
            Stream<String> lines = Files.lines(path);
            lines.forEach(line -> questions.add(createQuestionByRow(line.split(";"))));
            lines.close();
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Question createQuestionByRow(String[] row) {
        String question = row[0];
        int correctAnswer = Integer.parseInt(row[row.length - 1]);
        String[] answers = Arrays.copyOfRange(row, 1, row.length - 1);

        return new Question(question, answers, correctAnswer);
    }

}

package ru.otus.service;

import org.springframework.core.io.Resource;
import ru.otus.domain.Question;

import java.util.List;

public interface QuestionsReadService {
    List<Question> getAllQuestions(Resource resource);

}

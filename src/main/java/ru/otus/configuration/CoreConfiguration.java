package ru.otus.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoCSV;
import ru.otus.service.GameService;
import ru.otus.service.impl.GameServiceImpl;

@PropertySource("classpath:application.properties")
@Configuration
public class CoreConfiguration {

    @Bean
    public QuestionDao questionDao(@Value("${question.file-name}") String fileName) {
        return new QuestionDaoCSV(fileName);
    }

    @Bean
    public GameService gameService(QuestionDao questionDao) {
        return new GameServiceImpl(questionDao);
    }
}

package ru.otus.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoCSV;
import ru.otus.service.FileReaderService;
import ru.otus.service.GameService;
import ru.otus.service.IOService;
import ru.otus.service.impl.FileReaderServiceImpl;
import ru.otus.service.impl.GameServiceImpl;
import ru.otus.service.impl.IOServiceStreams;

@PropertySource("classpath:application.properties")
@Configuration
public class CoreConfiguration {

    @Bean
    public FileReaderService fileReaderService() {
        return new FileReaderServiceImpl();
    }

    @Bean
    public QuestionDao questionDao(@Value("${question.file-name}") String fileName,
                                   FileReaderService fileReaderService) {
        return new QuestionDaoCSV(fileName, fileReaderService);
    }

    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

    @Bean
    public GameService gameService(QuestionDao questionDao, IOService ioService) {
        return new GameServiceImpl(questionDao, ioService);
    }
}

package ru.otus.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

import static ru.otus.configuration.CoreProperty.PREFIX;

@ConfigurationProperties(prefix = PREFIX, ignoreUnknownFields = false)
public class CoreProperty {

    public static final String PREFIX = "application";

    private Locale locale;

    private Question question = new Question();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getQuestionsFileName() {
        return question.getFileName();
    }

    public static class Question {
        private String fileName;

        public Question() {
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}

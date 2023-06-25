package ru.otus.provider.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.configuration.CoreProperty;
import ru.otus.provider.FileNameProvider;
import ru.otus.provider.LocaleProvider;

@Component
@AllArgsConstructor
public class FileNameProviderImpl implements FileNameProvider {

    private static final String DEFAULT_QUESTION_FILE_TYPE_CSV = "csv";

    private static final String DEFAULT_QUESTION_FILE_NAME = "questions";

    private final LocaleProvider localeProvider;

    private final CoreProperty coreProperty;

    @Override
    public String getQuestionFileNameByCurrentLocale() {
        var questionsFileName = coreProperty.getQuestionsFileName();
        return questionsFileName != null ? questionsFileName : getFileName();
    }

    private String getFileName() {
        var locale = localeProvider.getLocale();
        return DEFAULT_QUESTION_FILE_NAME + "_" + locale.getLanguage() + "." + DEFAULT_QUESTION_FILE_TYPE_CSV;
    }
}

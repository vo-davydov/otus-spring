package ru.otus.core.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.core.MessageSourceHelper;
import ru.otus.provider.LocaleProvider;

@Component
@AllArgsConstructor
public class MessageSourceHelperImpl implements MessageSourceHelper {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    @Override
    public String getMessage(String code) {
        return getMessage(code, localeProvider.getLocale());
    }

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, code, localeProvider.getLocale());
    }

}

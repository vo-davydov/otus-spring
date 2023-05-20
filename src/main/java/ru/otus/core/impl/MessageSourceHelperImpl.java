package ru.otus.core.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.core.MessageSourceHelper;

import java.util.Locale;

@Component
public class MessageSourceHelperImpl implements MessageSourceHelper {

    private final MessageSource messageSource;

    @Value("${application.locale}")
    private Locale locale;

    public MessageSourceHelperImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, locale);
    }

    @Override
    public String getMessage(String code, Object... args) {
        if (args == null || args.length == 0) {
            return messageSource.getMessage(code, null, code, locale);
        } else {
            return messageSource.getMessage(code, args, code, locale);
        }
    }

}

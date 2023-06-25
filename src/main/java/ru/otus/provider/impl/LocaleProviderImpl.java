package ru.otus.provider.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.configuration.CoreProperty;
import ru.otus.provider.LocaleProvider;

import java.util.Locale;

@Component
@AllArgsConstructor
public class LocaleProviderImpl implements LocaleProvider {

    private final CoreProperty coreProperty;

    @Override
    public Locale getLocale() {
        var locale = coreProperty.getLocale();
        return locale != null ? locale : Locale.getDefault();
    }

}

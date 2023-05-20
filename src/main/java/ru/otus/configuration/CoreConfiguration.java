package ru.otus.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOService;
import ru.otus.service.impl.IOServiceStreams;

@Configuration
@EnableConfigurationProperties(CoreProperty.class)
public class CoreConfiguration {

    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

}

package ru.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static ru.otus.config.CoreProperties.PREFIX;

@Configuration
@ConfigurationProperties(PREFIX)
@PropertySource("classpath:application.yml")
@Getter
@Setter
public class CoreProperties {

    public static final String PREFIX = "core";

    private List<String> migrationPackages;

    @Value("${spring.data.mongodb.database}")
    private String dbName;
}

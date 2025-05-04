package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfig {

    final
    DbConfig dbConfig;

    final CorsConfig corsConfig;

    public AppConfig(
            CorsConfig corsConfig,
            DbConfig dbConfig
    ) {
        this.corsConfig = corsConfig;
        this.dbConfig = dbConfig;
    }
}
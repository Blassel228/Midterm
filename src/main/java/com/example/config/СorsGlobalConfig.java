package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class СorsGlobalConfig implements WebMvcConfigurer {

    private final AppConfig appConfig;

    public СorsGlobalConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins(appConfig.corsConfig.getOrigins())
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
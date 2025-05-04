package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.example.model")
@EnableJpaRepositories("com.example.repository")
@SpringBootApplication(scanBasePackages = "com.example")
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class Application {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().filename(".env").load();

        for (DotenvEntry entry : dotenv.entries()) {
            System.out.println("START ENV");
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + " " + entry.getValue());
            System.out.println("END ENV");
        }

        SpringApplication.run(Application.class, args);
    }
}
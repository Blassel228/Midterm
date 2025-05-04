package com.example.service;

import com.example.config.DbConfig;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final DbConfig dbConfig;

    public ConfigService(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public void printConfigs() {
        System.out.println("DB Host: " + dbConfig.getHost());
        System.out.println("DB Port: " + dbConfig.getPort());
    }
}
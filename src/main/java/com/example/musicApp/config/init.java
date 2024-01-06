package com.example.musicApp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class init {

    @Bean
    public CommandLineRunner commandLineRunner() {
        // insert contents for Demo
        return auth -> {
            System.out.println("cmd hello!");
        };
    }
}

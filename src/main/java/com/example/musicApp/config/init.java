package com.example.musicApp.config;

import com.example.musicApp.model.Role;
import com.example.musicApp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class init {

    private final RoleRepository roleRepository;

    public init(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        // insert contents for Demo
        return auth -> {
            System.out.println("cmd hello!");

            // initialize USER role
            roleRepository.save(new Role("USER"));
        };
    }
}

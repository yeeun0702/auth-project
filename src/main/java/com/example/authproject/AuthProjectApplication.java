package com.example.authproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class AuthProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthProjectApplication.class, args);
    }

}

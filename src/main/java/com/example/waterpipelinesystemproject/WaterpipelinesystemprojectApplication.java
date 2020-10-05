package com.example.waterpipelinesystemproject;

import com.example.waterpipelinesystemproject.service.InitDBService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
public class WaterpipelinesystemprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterpipelinesystemprojectApplication.class, args);
    }

    @Bean
    CommandLineRunner init(InitDBService initDBService) {
        return args -> initDBService.init();
    }

}

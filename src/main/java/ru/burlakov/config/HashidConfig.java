package ru.burlakov.config;

import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashidConfig {
    @Bean
    public Hashids create() {
        return new Hashids();
    }
}

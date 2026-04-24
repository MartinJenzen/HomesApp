package com.example.homes_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
    
    // Provides an ObjectMapper for the whole app for JSON de/serialization,
    // and auto-registers any Jackson modules present on the classpath
    // so that new datatypes de/serialize correctly without extra configuration.
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }
}
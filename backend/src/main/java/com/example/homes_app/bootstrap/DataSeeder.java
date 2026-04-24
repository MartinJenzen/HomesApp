package com.example.homes_app.bootstrap;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.homes_app.entity.HousingLocationEntity;
import com.example.homes_app.repository.HousingLocationJpaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataSeeder implements CommandLineRunner {
    
    private final HousingLocationJpaRepository jpaRepository;

    // ObjectMapper is a Jackson class used for converting between Java objects and JSON. 
    // It provides methods for reading JSON into Java objects and writing Java objects as JSON.
    private final ObjectMapper objectMapper; 

    public DataSeeder(HousingLocationJpaRepository jpaRepository, ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.objectMapper = objectMapper;
    }

    // Seed-only DTO that matches db.json exactly.
    // Used for reading seed data without affecting the main HousingLocation DTO or Entity.
    public record SeedLocation(
        int id,
        String name,
        String city,
        String state,
        String photo,
        int availableUnits,
        boolean wifi,
        boolean laundry
    ) {}

    // Checks if database is empty and if so, loads seed data from db.json and saves to DB using the JPA repository.
    @Override // Overrides run() of CommandLineRunner to execute code at application startup.
    public void run(String... args) throws Exception {
        
        if (jpaRepository.count() > 0)
            return;

        // Open the db.json file as an InputStream using ClassPathResource, which looks for the file in the classpath (e.g., src/main/resources)
        try (InputStream inputStream = new ClassPathResource("data/db.json").getInputStream()) { 
            // try-with-resources ensures the InputStream is closed after use
            
            // Parse JSON into a tree model so we can navigate through fields
            JsonNode root = objectMapper.readTree(inputStream);

            // Convert the "locations" array in the JSON into a List of HousingLocation objects
            List<SeedLocation> seedLocations = objectMapper.convertValue(root.get("locations"), 
                new TypeReference<List<SeedLocation>>() {}
            );

            // Map SeedLocation DTOs to HousingLocationEntity objects for saving to the database
            List<HousingLocationEntity> entities = seedLocations.stream()
                .map(location -> new HousingLocationEntity(
                    location.id(),
                    location.name(),
                    location.city(),
                    location.state(),
                    location.photo(),
                    location.availableUnits(),
                    location.wifi(),
                    location.laundry()
                ))
                .toList();

            // Save all entities to the database using the JPA repository
            jpaRepository.saveAll(entities);
        }
    }
}
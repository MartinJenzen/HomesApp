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
    
    private final ObjectMapper objectMapper; // Used for converting between Java objects and JSON
    private final HousingLocationJpaRepository jpaRepository;

    public DataSeeder(HousingLocationJpaRepository jpaRepository, ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.objectMapper = objectMapper;
    }

    // Seed-only DTO that matches db.json exactly
    public record SeedLocation(
        int id,
        String name,
        String city,
        String state,
        String photoKey,
        int availableUnits,
        boolean wifi,
        boolean laundry
    ) {}

    // Checks if database is empty and if so, loads seed data from db.json and saves to DB
    @Override
    public void run(String... args) throws Exception { // Is invoked on startup
        
        if (jpaRepository.count() > 0)
            return;

        // Opens db.json file as an InputStream, which is found in the classpath (/src/main/resources/...)
        try (InputStream inputStream = new ClassPathResource("data/db.json").getInputStream()) { 
            // try-with-resources ensures the InputStream is closed after use
            
            // Parses JSON into a tree model so that fields can be accessed individually
            JsonNode root = objectMapper.readTree(inputStream);

            // Converts the "locations" array in db.json into a list of HousingLocation objects
            List<SeedLocation> seedLocations = objectMapper.convertValue(root.get("locations"), 
                new TypeReference<List<SeedLocation>>() {}
            );

            // Maps SeedLocation DTOs to HousingLocationEntity objects for saving to the database
            List<HousingLocationEntity> entities = seedLocations.stream()
                .map(location -> new HousingLocationEntity(
                    location.id(),
                    location.name(),
                    location.city(),
                    location.state(),
                    location.photoKey(),
                    location.availableUnits(),
                    location.wifi(),
                    location.laundry()
                ))
                .toList();

            // Saves all entities to DB
            jpaRepository.saveAll(entities);
        }
    }
}
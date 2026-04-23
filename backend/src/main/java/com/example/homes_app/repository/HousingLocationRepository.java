package com.example.homes_app.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.example.homes_app.model.HousingLocation;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Repository
public class HousingLocationRepository {
    private final List<HousingLocation> locations;

    // Load housing location data from db.json file
    public HousingLocationRepository(ObjectMapper mapper) throws IOException {

        // Open the db.json file as an InputStream using ClassPathResource, which looks for the file in the classpath (e.g., src/main/resources)
        try (InputStream in = new ClassPathResource("data/db.json").getInputStream()) { // try-with-resources ensures the InputStream is closed after use
            
            // Parse JSON into a tree model so we can navigate through fields
            JsonNode root = mapper.readTree(in);
            
            // Convert the "locations" array in the JSON into a List of HousingLocation objects
            this.locations = mapper.convertValue(root.get("locations"),
                new TypeReference<List<HousingLocation>>() {}
            );
        }
    }

    public List<HousingLocation> findAll() {
        return locations;
    }

    public HousingLocation findById(int id) {
        return locations.stream().filter(location -> location.id() == id).findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Housing location not found with id: " + id));
    }
}
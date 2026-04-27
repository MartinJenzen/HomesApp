package com.example.homes_app.service;

import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.homes_app.dto.HousingLocation;
import com.example.homes_app.entity.HousingLocationEntity;
import com.example.homes_app.repository.HousingLocationJpaRepository;

@Service
public class HousingLocationService {
    
    private final HousingLocationJpaRepository jpaRepository;

    public HousingLocationService(HousingLocationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private HousingLocation mapEntityToDto(HousingLocationEntity entity) {
        return new HousingLocation(
                entity.getId(),
                entity.getName(),
                entity.getCity(),
                entity.getState(),
                entity.getPhotoKey(),
                entity.getAvailableUnits(),
                entity.getWifi(),
                entity.getLaundry()
        );
    }

    public List<HousingLocation> getAllLocations() {
        return jpaRepository.findAll().stream()
                // Converts each HousingLocationEntity to a HousingLocation DTO via mapEntityToDto()
                .map(this::mapEntityToDto)
                .toList();
    }

    public HousingLocation getLocationById(int id) {
        // Finds the entity by ID, maps it to a DTO if found, or returns null if not found
        return jpaRepository.findById(id).map(this::mapEntityToDto).orElse(null);
    }

    public record PhotoResource(Resource resource, MediaType mediaType) {}

    public PhotoResource getPhotoByLocationId(int id) {

        HousingLocationEntity entity = jpaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Housing location not found"));

        String photoKey = entity.getPhotoKey();
        if (photoKey == null || photoKey.isBlank()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo key missing");

        Resource resource = new ClassPathResource("static/images/" + photoKey);
        if (!resource.exists())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");

        MediaType mediaType = MediaTypeFactory.getMediaType(photoKey).orElse(MediaType.IMAGE_JPEG);

        return new PhotoResource(resource, mediaType);
    }
}
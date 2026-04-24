package com.example.homes_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
                entity.getPhoto(),
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
}
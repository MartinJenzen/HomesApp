package com.example.homes_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.homes_app.model.HousingLocation;
import com.example.homes_app.repository.HousingLocationRepository;

@Service
public class HousingLocationService {
    
    private final HousingLocationRepository repository;

    public HousingLocationService(HousingLocationRepository repository) {
        this.repository = repository;
    }

    public List<HousingLocation> getAllLocations() {
        return repository.findAll();
    }

    public HousingLocation getLocationById(int id) {
        return repository.findById(id);
    }
}

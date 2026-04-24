package com.example.homes_app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.homes_app.dto.HousingLocation;
import com.example.homes_app.service.HousingLocationService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular frontend on port 4200 to backend on port 8080
@RestController
public class HousingLocationController {

    private final HousingLocationService housingService;

    public HousingLocationController(HousingLocationService housingService) {
        this.housingService = housingService;
    }
    
    @GetMapping("/locations")
    public List<HousingLocation> getHousingLocations() {
        return housingService.getAllLocations();
    }

    @GetMapping("/locations/{id}")
    public HousingLocation getHousingLocationById(@PathVariable int id) {
        return housingService.getLocationById(id);
    }
}
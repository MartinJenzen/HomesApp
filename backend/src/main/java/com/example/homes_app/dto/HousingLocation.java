package com.example.homes_app.dto;

// DTO (Data Transfer Object) representing a housing location, used for transferring data between layers of the application
public record HousingLocation(
    int id,
    String name,
    String city,
    String state,
    String photo,
    int availableUnits,
    boolean wifi,
    boolean laundry
){}
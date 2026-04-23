package com.example.homes_app.model;

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
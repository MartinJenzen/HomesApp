package com.example.homes_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Represents the housing location to persist in DB via Hibernate (ORM)
@Entity
@Table(name = "housing_location")
public class HousingLocationEntity {
    
    @Id
    private Integer id;

    private String name;
    private String city;
    private String state;
    private String photo;

    @Column(name = "available_units")
    private Integer availableUnits;

    private Boolean wifi;
    private Boolean laundry;

    protected HousingLocationEntity() {} // JPA requires a default constructor

    public HousingLocationEntity(Integer id, String name, String city, String state, String photo, Integer availableUnits, Boolean wifi, Boolean laundry) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.photo = photo;
        this.availableUnits = availableUnits;
        this.wifi = wifi;
        this.laundry = laundry;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhoto() {
        return photo;
    }

    public Integer getAvailableUnits() {
        return availableUnits;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public Boolean getLaundry() {
        return laundry;
    }
}
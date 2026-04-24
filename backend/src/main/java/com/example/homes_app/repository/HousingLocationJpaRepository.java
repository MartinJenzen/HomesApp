package com.example.homes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.homes_app.entity.HousingLocationEntity;

// JpaRepository provides CRUD operations for HousingLocationEntity without needing to implement any methods. 
// Spring Data JPA will automatically generate the necessary code at runtime based on the method signatures defined in this interface. 
// The generic parameters specify the entity type (HousingLocationEntity) and the type of its primary key (Integer).
public interface HousingLocationJpaRepository extends JpaRepository<HousingLocationEntity, Integer> {
}
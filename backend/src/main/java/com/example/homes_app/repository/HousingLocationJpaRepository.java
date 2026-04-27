package com.example.homes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.homes_app.entity.HousingLocationEntity;

// JpaRepository provides CRUD operations
public interface HousingLocationJpaRepository extends JpaRepository<HousingLocationEntity, Integer> {}
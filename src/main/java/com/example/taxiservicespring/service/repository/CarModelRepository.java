package com.example.taxiservicespring.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taxiservicespring.service.model.CarModel;

public interface CarModelRepository extends JpaRepository<CarModel, Long>{
}

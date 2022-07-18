package com.example.taxiservicespring.service.repository;

import java.util.Optional;

import com.example.taxiservicespring.service.model.CarModel;

public interface CarModelRepository {
    
    Optional<CarModel> findById(long id);
}

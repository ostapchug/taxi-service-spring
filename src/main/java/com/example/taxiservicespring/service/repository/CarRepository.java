package com.example.taxiservicespring.service.repository;

import java.util.List;

import com.example.taxiservicespring.service.model.Car;

public interface CarRepository {

    Car findById(long id);

    Car findByCategoryAndCapacity(long categoryId, int capacity);

    Car findByCapacity(int capacity);

    List<Car> findAllByCategoryAndCapacity(long categoryId, int capacity);
}

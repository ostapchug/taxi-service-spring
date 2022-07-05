package com.example.taxiservicespring.service.repository;

import java.util.List;

import com.example.taxiservicespring.service.model.Car;

public interface CarRepository {

    Car find(long id);

    Car find(long categoryId, int capacity);

    Car findByCapacity(int capacity);

    List<Car> findCars(long categoryId, int capacity);
}

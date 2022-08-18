package com.example.taxiservicespring.service.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarStatus;
import com.example.taxiservicespring.service.repository.CarModelRepository;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.util.DataGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository {
    private final List<Car> cars = DataGenerator.createCars();
    private final CarModelRepository carModelRepository;

    @Override
    public Car find(long id) {
        log.info("find car with id {}", id);
        return cars.stream()
                .filter(car -> car.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car is not found!"));
    }

    @Override
    public Car find(long categoryId, int capacity) {
        log.info("find car by category id {} and capacity {}", categoryId, capacity);
        return cars.stream()
                .filter(car -> car.getCategoryId() == categoryId
                        && carModelRepository.find(car.getModelId()).getSeatCount() >= capacity
                        && car.getStatus().equals(CarStatus.READY))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car is not found!"));
    }

    @Override
    public Car findByCapacity(int capacity) {
        log.info("find car by capacity {}", capacity);
        return cars.stream()
                .filter(car -> carModelRepository.find(car.getModelId()).getSeatCount() >= capacity
                        && car.getStatus().equals(CarStatus.READY))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car is not found!"));
    }

    @Override
    public List<Car> findCars(long categoryId, int capacity) {
        log.info("find cars by category id {} and capacity {}", categoryId, capacity);
        List<Car> result = new ArrayList<>();
        int currentCapacity = 0;
        
        for (Car car : cars) {
            if (car.getCategoryId() == categoryId && car.getStatus().equals(CarStatus.READY)) {
                if (currentCapacity >= capacity) {
                    break;
                }
                currentCapacity += carModelRepository.find(car.getModelId()).getSeatCount();
                result.add(car);
            }
        }

        if (currentCapacity < capacity) {
            throw new RuntimeException("Not enough cars in this category!");
        }
        return result;
    }
}

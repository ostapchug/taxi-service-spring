package com.example.taxiservicespring.service.repository.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.repository.CarModelRepository;
import com.example.taxiservicespring.util.DataGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CarModelRepositoryImpl implements CarModelRepository {
    private final List<CarModel> carModels = DataGenerator.createCarModels();

    @Override
    public CarModel find(long id) {
        log.info("find car model with id {}", id);
        return carModels.stream()
                .filter(carModel -> carModel.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CarModel is not found!"));
    }
}

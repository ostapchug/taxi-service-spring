package com.example.taxiservicespring.service.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.repository.CarModelRepository;
import com.example.taxiservicespring.util.DataGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CarModelRepositoryImpl implements CarModelRepository {
    private final List<CarModel> carModels = DataGenerator.createCarModels();

    @Override
    public CarModel findById(long id) {
        log.info("find car model with id {}", id);
        return carModels.stream()
                .filter(carModel -> carModel.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CarModel is not found!"));
    }
}

package com.example.taxiservicespring.service.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.repository.CarModelRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CarModelRepositoryImpl implements CarModelRepository {
    private final List<CarModel> carModels = new ArrayList<>();

    CarModelRepositoryImpl() {
        CarModel carModel = new CarModel();
        carModel.setId(1L);
        carModel.setBrand("Ford");
        carModel.setName("Focus");
        carModel.setColor("Blue");
        carModel.setYear(2012);
        carModel.setSeatCount(3);
        carModels.add(carModel);

        carModel = new CarModel();
        carModel.setId(2L);
        carModel.setBrand("Volkswagen");
        carModel.setName("Caddy");
        carModel.setColor("Silver");
        carModel.setYear(2021);
        carModel.setSeatCount(6);
        carModels.add(carModel);

        carModel = new CarModel();
        carModel.setId(3L);
        carModel.setBrand("Audi");
        carModel.setName("A6");
        carModel.setColor("Black");
        carModel.setYear(2011);
        carModel.setSeatCount(4);
        carModels.add(carModel);
    }

    @Override
    public CarModel find(long id) {
        log.info("find car model with id {}", id);
        return carModels.stream()
                .filter(carModel -> carModel.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("CarModel is not found!"));
    }
}

package com.example.taxiservicespring.service.repository;

import com.example.taxiservicespring.service.model.CarModel;

public interface CarModelRepository {

    CarModel findById(long id);
}

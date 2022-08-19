package com.example.taxiservicespring.service;

import java.util.List;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;

public interface TripService {

    TripDto find(long id);

    List<CarDto> findCarsByTripId(long tripId);

    TripConfirmDto create(TripCreateDto tripCreateDto);

    TripDto confirm(TripConfirmDto tripConfirmDto);

    TripDto updateStatus(long tripId, String status);

    List<TripDto> getAll();

    List<TripDto> getAllByPersonId(long personId);

    List<TripDto> getAllByDate(String dateRange);

    List<TripDto> getAllByPersonIdAndDate(long personId, String dateRange);
}

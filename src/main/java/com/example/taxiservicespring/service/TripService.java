package com.example.taxiservicespring.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.model.TripStatus;

public interface TripService {

    TripDto getById(long id);

    List<TripDto> getAll();

    List<TripDto> getAllByPersonId(long personId);

    List<TripDto> getAllByDate(LocalDateTime[] dateRange);

    List<TripDto> getAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange);

    List<CarDto> getCarsByTripId(long id);

    TripConfirmDto create(TripCreateDto tripCreateDto);

    TripDto confirm(TripConfirmDto tripConfirmDto);

    TripDto updateStatus(long id, TripStatus status);
}

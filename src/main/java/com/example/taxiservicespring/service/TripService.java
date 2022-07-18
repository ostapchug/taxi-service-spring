package com.example.taxiservicespring.service;

import org.springframework.data.domain.Page;

import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;

public interface TripService {

    TripDto find(long id);

    TripConfirmDto create(TripCreateDto tripCreateDto);

    TripDto confirm(TripConfirmDto tripConfirmDto);

    TripDto updateStatus(long tripId, String status);

    Page<TripDto> getAll(int page, int count, String sorting);

    Page<TripDto> getAllByPersonId(long personId, int page, int count, String sorting);

    Page<TripDto> getAllByDate(String dateRange, int page, int count, String sorting);

    Page<TripDto> getAllByPersonIdAndDate(long personId, String dateRange, int page, int count, String sorting);
}

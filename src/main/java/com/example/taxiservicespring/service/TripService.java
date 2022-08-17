package com.example.taxiservicespring.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;

public interface TripService {

    TripDto find(long id);

    TripConfirmDto create(TripCreateDto tripCreateDto);

    TripDto confirm(TripConfirmDto tripConfirmDto);

    TripDto updateStatus(long tripId, String status);

    Page<TripDto> getAll(Pageable pageable);

    Page<TripDto> getAllByPersonId(long personId, Pageable pageable);

    Page<TripDto> getAllByDate(String dateRange, Pageable pageable);

    Page<TripDto> getAllByPersonIdAndDate(long personId, String dateRange, Pageable pageable);
}

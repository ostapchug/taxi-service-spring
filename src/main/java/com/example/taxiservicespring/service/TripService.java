package com.example.taxiservicespring.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.model.TripStatus;

public interface TripService {

    TripDto getById(long id);

    Page<TripDto> getAll(Pageable pageable);

    Page<TripDto> getAllByPersonId(long personId, Pageable pageable);

    Page<TripDto> getAllByDate(LocalDateTime[] dateRange, Pageable pageable);

    Page<TripDto> getAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange, Pageable pageable);

    TripConfirmDto create(TripCreateDto tripCreateDto);

    TripDto confirm(TripConfirmDto tripConfirmDto);

    TripDto updateStatus(long id, TripStatus status);
}

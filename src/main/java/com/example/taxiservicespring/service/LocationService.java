package com.example.taxiservicespring.service;

import java.util.List;

import com.example.taxiservicespring.controller.dto.LocationDto;

public interface LocationService {
	
	LocationDto find(long id);
	
	List<LocationDto> getAll();

}

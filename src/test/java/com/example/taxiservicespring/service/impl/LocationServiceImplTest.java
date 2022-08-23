package com.example.taxiservicespring.service.impl;

import static com.example.taxiservicespring.util.TestDataUtil.createLocation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    void findTest() {
        Location location = createLocation();
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.find(anyLong());

        assertThat(locationDto, allOf(
                hasProperty("id", equalTo(location.getId())),
                hasProperty("streetName", equalTo(location.getStreetName())),
                hasProperty("streetNumber",  equalTo(location.getStreetNumber()))
                ));
    }

    @Test
    void findLocationNotFoundTest() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> locationService.find(anyLong()));
    }

    @Test
    void getAllTest() {
        Location location = createLocation();
        when(locationRepository.findAll()).thenReturn(List.of(location));

        List<LocationDto> locationDtoList = locationService.getAll();

        assertThat(locationDtoList, hasSize(1));
        assertThat(locationDtoList.get(0), allOf(
                hasProperty("id", equalTo(location.getId())),
                hasProperty("streetName", equalTo(location.getStreetName())),
                hasProperty("streetNumber",  equalTo(location.getStreetNumber()))
                ));
    }
}

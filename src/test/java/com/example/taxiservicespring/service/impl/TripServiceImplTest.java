package com.example.taxiservicespring.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;
import static com.example.taxiservicespring.util.TestDataUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.exception.DataProcessingException;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.mapper.TripMapper;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarStatus;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.service.repository.CategoryRepository;
import com.example.taxiservicespring.service.repository.LocationRepository;
import com.example.taxiservicespring.service.repository.PersonRepository;
import com.example.taxiservicespring.service.repository.TripRepository;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {
    
    @Mock
    private CarRepository carRepository;
    
    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private LocationRepository locationRepository;
    
    @Mock
    private PersonRepository personRepository;
    
    @Mock
    private TripRepository tripRepository;
    
    @Mock
    private TripMapper tripMapper;
    
    @Mock
    private Map<BigDecimal, BigDecimal> discounts;
    
    @InjectMocks
    private TripServiceImpl tripService;

    @Test
    void findTest() {
        Trip trip = createTrip();
        TripDto testTripDto = createTripDto();
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
        when(tripMapper.mapTripDto(trip)).thenReturn(testTripDto);
        
        TripDto tripDto = tripService.find(anyLong());
        
        assertThat(tripDto, equalTo(testTripDto));
    }
    
    @Test
    void findNotFoundTest() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());        
        assertThrows(EntityNotFoundException.class, () -> tripService.find(anyLong()));
    }
    
    @Test
    void createTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        TripConfirmDto testTripConfirmDto = createTripConfirmDto();
        Category category = createCategory();
        Car car = createCar(); 
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(carRepository.findByCategoryIdAndStatusAndCapacity(anyInt(), any(), anyInt())).thenReturn(Optional.of(car));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createDestination()));
        when(tripRepository.getTotalBill(anyLong(), any())).thenReturn(Optional.of(TOTAL_BILL));
        when(discounts.entrySet()).thenReturn(DISCOUNTS.entrySet());
        
        TripConfirmDto tripConfirmDto = tripService.create(tripCreateDto);
        
        assertThat(tripConfirmDto, equalTo(testTripConfirmDto));  
    }
    
    @Test
    void createCategoryNotFoundTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        Category category = createCategory();
        Car car = createCar();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty()); 
        when(carRepository.findByCategoryIdAndStatusAndCapacity(anyInt(), any(), anyInt())).thenReturn(Optional.of(car));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createDestination()));
        
        assertThrows(EntityNotFoundException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createCarNotFoundTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        Category category = createCategory();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findByCategoryIdAndStatusAndCapacity(anyInt(), any(), anyInt())).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createOriginNotFoundTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        Category category = createCategory();
        Car car = createCar();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findByCategoryIdAndStatusAndCapacity(anyInt(), any(), anyInt())).thenReturn(Optional.of(car));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createDestinationNotFoundTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        Category category = createCategory();
        Car car = createCar();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findByCategoryIdAndStatusAndCapacity(anyInt(), any(), anyInt())).thenReturn(Optional.of(car));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createDistanceNotEnoughTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        Category category = createCategory();
        Car car = createCar();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findByCategoryIdAndStatusAndCapacity(anyInt(), any(), anyInt())).thenReturn(Optional.of(car));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createOrigin()));
        
        assertThrows(DataProcessingException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createUseMultipleCarsTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setCapacity(5);
        tripCreateDto.setMultipleCars(true);
        TripConfirmDto testTripConfirmDto = createTripConfirmDtoWithMultipleCars();
        Category category = createCategory();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(carRepository.findAllByCategoryIdAndStatus(anyInt(), any())).thenReturn(createCarList());
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createDestination()));
        when(tripRepository.getTotalBill(anyLong(), any())).thenReturn(Optional.of(TOTAL_BILL));
        when(discounts.entrySet()).thenReturn(DISCOUNTS.entrySet());
        
        TripConfirmDto tripConfirmDto = tripService.create(tripCreateDto);
        
        assertThat(tripConfirmDto, equalTo(testTripConfirmDto));  
    }
    
    @Test
    void createUseMultipleCarsCapacityEqualTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setCapacity(7);
        tripCreateDto.setMultipleCars(true);
        TripConfirmDto testTripConfirmDto = createTripConfirmDtoWithMultipleCarsCapacityEqual();
        Category category = createCategory();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(carRepository.findAllByCategoryIdAndStatus(anyInt(), any())).thenReturn(createCarList());
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createDestination()));
        when(tripRepository.getTotalBill(anyLong(), any())).thenReturn(Optional.of(TOTAL_BILL));
        when(discounts.entrySet()).thenReturn(DISCOUNTS.entrySet());
        
        TripConfirmDto tripConfirmDto = tripService.create(tripCreateDto);
        
        assertThat(tripConfirmDto, equalTo(testTripConfirmDto));  
    }
    
    @Test
    void createUseMultipleCarsCarsNotFoundTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setMultipleCars(true);
        Category category = createCategory();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findAllByCategoryIdAndStatus(anyInt(), any())).thenReturn(new ArrayList<>());
        
        assertThrows(DataProcessingException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createUseMultipleCarsCarsNotEnoughTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setCapacity(15);
        tripCreateDto.setMultipleCars(true);
        Category category = createCategory();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findAllByCategoryIdAndStatus(anyInt(), any())).thenReturn(createCarList());
        
        assertThrows(DataProcessingException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void createIgnoreCategoryTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setIgnoreCategory(true);
        TripConfirmDto testTripConfirmDto = createTripConfirmDto();
        Category category = createCategory();
        Car car = createCar(); 
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(carRepository.findByStatusAndCapacity(any(), anyInt())).thenReturn(Optional.of(car));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createDestination()));
        when(tripRepository.getTotalBill(anyLong(), any())).thenReturn(Optional.of(TOTAL_BILL));
        when(discounts.entrySet()).thenReturn(DISCOUNTS.entrySet());
        
        TripConfirmDto tripConfirmDto = tripService.create(tripCreateDto);
        
        assertThat(tripConfirmDto, equalTo(testTripConfirmDto));
    }
    
    @Test
    void createIgnoreCategoryCarNotFoundTest() {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setIgnoreCategory(true);
        Category category = createCategory();
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(carRepository.findByStatusAndCapacity(any(), anyInt())).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> tripService.create(tripCreateDto));
    }
    
    @Test
    void confirmTest() {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        TripDto testTripDto = createTripDto();
        Trip trip = createTrip();
        Person person = createPerson();
        Category category = createCategory();
        Car car = mock(Car.class);
        when(car.getCategory()).thenReturn(category);
        when(car.getStatus()).thenReturn(CarStatus.READY);
        when(carRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(car));
        when(categoryRepository.getReferenceById(anyInt())).thenReturn(category);
        when(personRepository.getReferenceById(anyLong())).thenReturn(person);
        when(locationRepository.getReferenceById(ORIGIN_ID)).thenReturn(createOrigin());
        when(locationRepository.getReferenceById(DEST_ID)).thenReturn(createDestination());
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(locationRepository.findById(ORIGIN_ID)).thenReturn(Optional.of(createOrigin()));
        when(locationRepository.findById(DEST_ID)).thenReturn(Optional.of(createDestination()));
        when(tripRepository.getTotalBill(anyLong(), any())).thenReturn(Optional.of(TOTAL_BILL));
        when(discounts.entrySet()).thenReturn(DISCOUNTS.entrySet());
        when(tripRepository.save(any())).thenReturn(trip);
        when(tripMapper.mapTripDto(any())).thenReturn(testTripDto);
        
        TripDto tripDto = tripService.confirm(tripConfirmDto);
        
        verify(car, times(1)).setStatus(CarStatus.BUSY);        
        assertThat(tripDto, equalTo(testTripDto));     
    }
    
    @Test
    void confirmCarNotFoundTest() {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        when(carRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.empty()); 
        
        assertThrows(EntityNotFoundException.class, () -> tripService.confirm(tripConfirmDto));     
    }
    
    @Test
    void confirmCarDoesntBelongToCategoryTest() {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        Car car = createCar();
        car.getCategory().setId(2);
        when(carRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(car)); 
        
        assertThrows(DataProcessingException.class, () -> tripService.confirm(tripConfirmDto));     
    }
    
    @Test
    void confirmCarIsBusyTest() {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        Car car = createCar();
        car.setStatus(CarStatus.BUSY);
        when(carRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(car)); 
        
        assertThrows(DataProcessingException.class, () -> tripService.confirm(tripConfirmDto));     
    }
    
    @Test
    void getAllTest() {
        TripDto tripDto = createTripDto(); 
        Trip trip = createTrip();
        when(tripRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(trip)));
        when(tripMapper.mapTripDto(any())).thenReturn(tripDto);
        
        Page<TripDto> page = tripService.getAll(PAGEABLE); 
        
        assertThat(page, allOf(
                hasProperty("totalElements", is(1L)),
                hasProperty("number", is(0)),
                hasProperty("size", is(2)),
                hasProperty("sort", is(SORTING)),
                hasProperty("content", is(List.of(tripDto)))
                ));
    }

    @Test
    void getAllByPersonIdTest() {
        TripDto tripDto = createTripDto(); 
        Trip trip = createTrip();
        when(tripRepository.findAllByPersonId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(trip)));
        when(tripMapper.mapTripDto(any())).thenReturn(tripDto);
        
        Page<TripDto> page = tripService.getAllByPersonId(ID, PAGEABLE); 
        
        assertThat(page, allOf(
                hasProperty("totalElements", is(1L)),
                hasProperty("number", is(0)),
                hasProperty("size", is(2)),
                hasProperty("sort", is(SORTING)),
                hasProperty("content", is(List.of(tripDto)))
                ));
    }

    @Test
    void getAllByDateTest() {
        TripDto tripDto = createTripDto(); 
        Trip trip = createTrip();
        when(tripRepository.findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class),
                any(Pageable.class))).thenReturn(new PageImpl<>(List.of(trip)));
        when(tripMapper.mapTripDto(any())).thenReturn(tripDto);
        
        Page<TripDto> page = tripService.getAllByDate(DATE_RANGE, PAGEABLE); 
        
        assertThat(page, allOf(
                hasProperty("totalElements", is(1L)),
                hasProperty("number", is(0)),
                hasProperty("size", is(2)),
                hasProperty("sort", is(SORTING)),
                hasProperty("content", is(List.of(tripDto)))
                ));
    }
    
    @Test
    void getAllByPersonIdAndDateTest() {
        TripDto tripDto = createTripDto(); 
        Trip trip = createTrip();
        when(tripRepository.findAllByPersonIdAndDateBetween(anyLong(), any(LocalDateTime.class),
                any(LocalDateTime.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(trip)));
        when(tripMapper.mapTripDto(any())).thenReturn(tripDto);
        
        Page<TripDto> page = tripService.getAllByPersonIdAndDate(ID, DATE_RANGE, PAGEABLE); 
        
        assertThat(page, allOf(
                hasProperty("totalElements", is(1L)),
                hasProperty("number", is(0)),
                hasProperty("size", is(2)),
                hasProperty("sort", is(SORTING)),
                hasProperty("content", is(List.of(tripDto)))
                ));
    }

    @Test
    void updateStatusTest() {
        TripDto testTripDto = createTripDto();
        testTripDto.setStatus(TripStatus.ACCEPTED);
        Trip trip = mock(Trip.class);
        when(tripRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(trip));
        when(tripMapper.mapTripDto(any())).thenReturn(testTripDto);
        when(trip.getStatus()).thenReturn(TripStatus.NEW);
        
        TripDto tripDto = tripService.updateStatus(ID, TripStatus.ACCEPTED.name());
        
        verify(trip, times(1)).setStatus(TripStatus.ACCEPTED);
        verify(trip, never()).getCars();
        assertThat(tripDto, equalTo(testTripDto)); 
    }
    
    @Test
    void updateStatusAndUnlockCarsTest() {
        TripDto testTripDto = createTripDto();
        testTripDto.setStatus(TripStatus.COMPLETED);
        Trip trip = mock(Trip.class);
        Car car = mock(Car.class);
        when(tripRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(trip));
        when(tripMapper.mapTripDto(any())).thenReturn(testTripDto);
        when(trip.getStatus()).thenReturn(TripStatus.ACCEPTED);
        when(trip.getCars()).thenReturn(Set.of(car));
        
        TripDto tripDto = tripService.updateStatus(ID, TripStatus.COMPLETED.name());
        verify(trip, times(1)).setStatus(TripStatus.COMPLETED);
        verify(car, times(1)).setStatus(CarStatus.READY);
        assertThat(tripDto, equalTo(testTripDto)); 
    }
    
    @Test
    void updateStatusTripNotFoundTest() {
        when(tripRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());        
        assertThrows(EntityNotFoundException.class, () -> tripService.updateStatus(ID, TripStatus.ACCEPTED.name()));
    }
    
    @Test
    void updateStatusTripAlreadyDoneTest() {
        when(tripRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());
        Trip trip = createTrip();
        trip.setStatus(TripStatus.COMPLETED);
        when(tripRepository.findByIdForUpdate(anyLong())).thenReturn(Optional.of(trip));
        
        assertThrows(DataProcessingException.class, () -> tripService.updateStatus(ID, TripStatus.ACCEPTED.name()));
    }
}

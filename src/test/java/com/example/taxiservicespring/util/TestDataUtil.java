package com.example.taxiservicespring.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.mapper.CarMapper;
import com.example.taxiservicespring.service.mapper.CarModelMapper;
import com.example.taxiservicespring.service.mapper.CategoryMapper;
import com.example.taxiservicespring.service.mapper.LocationMapper;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.model.Role;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataUtil {
    private static final String DATE_FORMAT = "dd.MM.yy";
    public static final long ID = 1L;
    public static final String PHONE = "0123456780";
    public static final String PASSWORD = "Client#0";
    public static final String NAME = "John";
    public static final String SURNAME = "Doe";
    public static final Sort SORTING = Sort.by("date").descending();
    public static final Pageable PAGEABLE= PageRequest.of(0, 2, SORTING);
    public static final long PERSON_ID = 1L;
    public static final long ORIGIN_ID = 1L;
    public static final int CATEGORY_ID = 1;
    public static final int CAPACITY = 1;
    public static final long DEST_ID = 4L;
    public static final BigDecimal DISTANCE = BigDecimal.ONE.setScale(2);
    public static final BigDecimal PRICE = BigDecimal.valueOf(25).setScale(2);
    public static final BigDecimal BILL = BigDecimal.valueOf(24.5).setScale(2);
    public static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.5).setScale(2);
    public static final BigDecimal TOTAL_BILL = BigDecimal.valueOf(100).setScale(2);
    public static final LocalDateTime DATE = LocalDateTime.now();
    public static final String DATE_RANGE = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + "-"
            + LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    public static final Map<BigDecimal, BigDecimal> DISCOUNTS = Map.of(
            BigDecimal.valueOf(100), BigDecimal.valueOf(0.02),
            BigDecimal.valueOf(500), BigDecimal.valueOf(0.05),
            BigDecimal.valueOf(1000), BigDecimal.valueOf(0.10)
            );
    
    public static Location createOrigin() {
        return Location.builder()
                .id(1L)
                .streetName("Molodizhna")
                .streetNumber("36")
                .latitude(BigDecimal.valueOf(48.925541084296924))
                .longitude(BigDecimal.valueOf(24.737374909778257))
                .build();
    }
    
    public static Location createDestination() {
        return Location.builder()
                .id(4L)
                .streetName("Sofiivs'kyi Ln")
                .streetNumber("18")
                .latitude(BigDecimal.valueOf(48.92899254668747))
                .longitude(BigDecimal.valueOf(24.72473578396449))
                .build();
    }
    
    public static Person createPerson() {
        return Person.builder()
                .id(ID)
                .phone(PHONE)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .build();
    }
    
    public static CarModel createCarModel() {
        return CarModel.builder()
                .id(1L)
                .brand("Ford")
                .name("Focus")
                .year(2012)
                .color("Blue")
                .seatCount(3)
                .build();
    }
    
    public static CarModel createCarModelWithBiggerCapacity() {
        return CarModel.builder()
                .id(2L)
                .brand("Ford")
                .name("Fusion")
                .year(2016)
                .color("Blue")
                .seatCount(4)
                .build();
    }
    
    public static Category createCategory() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name("Economy")
                .price(BigDecimal.valueOf(25))
                .build();
    }
    
    public static Location createLocation() {
        return createOrigin();
    }
    
    public static Car createCar() {
        return Car.builder()
                .id(1L)
                .regNumber("AA1234TV")
                .model(createCarModel())
                .category(createCategory())
                .location(createLocation())
                .build();
    }
    
    public static Trip createTrip() {
        return Trip.builder()
                .id(1L)
                .person(createPerson())
                .origin(createOrigin())
                .destination(createDestination())
                .distance(DISTANCE)
                .date(DATE)
                .bill(BILL)
                .cars(Set.of(createCar()))
                .build();
    }
    
    public static PersonDto createPersonDto() {
        return PersonDto.builder()
                .id(ID)
                .phone(PHONE)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .role(Role.CLIENT)
                .build();
    }
    
    public static List<Car> createCarList(){
        List<Car> result = new ArrayList<>();
        CarModel carModel = CarModel.builder()
                .id(1L)
                .brand("Ford")
                .name("Focus")
                .year(2012)
                .color("Blue")
                .seatCount(3)
                .build();
        CarModel carModel2 = CarModel.builder()
                .id(2L)
                .brand("Ford")
                .name("Fusion")
                .year(2016)
                .color("Blue")
                .seatCount(4)
                .build();
        
        Car car = Car.builder()
                .id(1L)
                .regNumber("AA1234TV")
                .model(carModel)
                .category(createCategory())
                .location(createLocation())
                .build();
        result.add(car);
        car = Car.builder()
                .id(2L)
                .regNumber("AA1235TV")
                .model(carModel2)
                .category(createCategory())
                .location(createLocation())
                .build();
        result.add(car);
        car = Car.builder()
                .id(3L)
                .regNumber("AA1236TV")
                .model(carModel)
                .category(createCategory())
                .location(createLocation())
                .build();
        result.add(car);
        car = Car.builder()
                .id(4L)
                .regNumber("AA1237TV")
                .model(carModel2)
                .category(createCategory())
                .location(createLocation())
                .build();
        result.add(car);
  
        return result;
    }
    
    public static List<CarDto> createCarDtoList(){
        return createCarList()
                .stream()
                .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                .collect(Collectors.toList());
    }
    
    public static CarDto createCarDto() {
        return CarMapper.INSTANCE.mapCarDto(createCar());
    }
    
    public static CategoryDto createCategoryDto() {
        return CategoryMapper.INSTANCE.mapCategoryDto(createCategory());
    }
    
    public static CarModelDto createCarModelDto() {
        return CarModelMapper.INSTANCE.mapCarModelDto(createCarModel());
    }
    
    public static LocationDto createLocationDto() {
        return LocationMapper.INSTANCE.mapLocationDto(createLocation());
    }
    
    public static TripDto createTripDto() {
        return TripDto.builder()
                .id(1L)
                .personId(PERSON_ID)
                .originId(ORIGIN_ID)
                .destinationId(DEST_ID)
                .distance(DISTANCE)
                .date(DATE)
                .bill(BILL)
                .status(TripStatus.NEW)
                .cars(Set.of(createCarDto()))
                .build();
    }
    
    public static TripCreateDto createTripCreateDto() {
        return TripCreateDto.builder()
                .personId(PERSON_ID)
                .originId(ORIGIN_ID)
                .destinationId(DEST_ID)
                .categoryId(CATEGORY_ID)
                .capacity(CAPACITY)
                .build();      
    }
    
    public static TripConfirmDto createTripConfirmDto() {
        return TripConfirmDto.builder()
                .personId(PERSON_ID)
                .originId(ORIGIN_ID)
                .destinationId(DEST_ID)
                .categoryId(CATEGORY_ID)
                .distance(DISTANCE)
                .price(PRICE)
                .discount(DISCOUNT)
                .total(BILL)
                .waitTime(LocalTime.MIDNIGHT)
                .cars(List.of(createCarDto()))
                .build();      
    }
    
    public static TripConfirmDto createTripConfirmDtoWithMultipleCars() {
        List<CarDto> cars = createCarDtoList();        
        return TripConfirmDto.builder()
                .personId(PERSON_ID)
                .originId(ORIGIN_ID)
                .destinationId(DEST_ID)
                .categoryId(CATEGORY_ID)
                .distance(DISTANCE)
                .price(BigDecimal.valueOf(50).setScale(2))
                .discount(BigDecimal.ONE.setScale(2))
                .total(BigDecimal.valueOf(49).setScale(2))
                .waitTime(LocalTime.MIDNIGHT)
                .cars(List.of(cars.get(1), cars.get(2)))
                .build();      
    }
    
    public static TripConfirmDto createTripConfirmDtoWithMultipleCarsCapacityEqual() {
        List<CarDto> cars = createCarDtoList();        
        return TripConfirmDto.builder()
                .personId(PERSON_ID)
                .originId(ORIGIN_ID)
                .destinationId(DEST_ID)
                .categoryId(CATEGORY_ID)
                .distance(DISTANCE)
                .price(BigDecimal.valueOf(50).setScale(2))
                .discount(BigDecimal.ONE.setScale(2))
                .total(BigDecimal.valueOf(49).setScale(2))
                .waitTime(LocalTime.MIDNIGHT)
                .cars(List.of(cars.get(1), cars.get(0)))
                .build();      
    }
}

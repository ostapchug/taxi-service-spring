package com.example.taxiservicespring.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.TripService;
import com.example.taxiservicespring.service.mapper.CarMapper;
import com.example.taxiservicespring.service.mapper.TripMapper;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.service.repository.CategoryRepository;
import com.example.taxiservicespring.service.repository.LocationRepository;
import com.example.taxiservicespring.service.repository.TripRepository;
import com.example.taxiservicespring.util.DistanceCalculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private static final String DATE_FORMAT = "dd.MM.yy";
    private static final BigDecimal MIN_DICTANCE = BigDecimal.ONE;
    private static final BigDecimal AVG_SPEED = BigDecimal.valueOf(0.3); // car average speed in km/min
    private static final int SCALE = 2;
    @Value("#{${discount}}")
    private final Map<BigDecimal, BigDecimal> discounts;
    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final CarRepository carRepository;

    @Override
    public TripDto find(long id) {
        log.info("get trip by id {}", id);
        Trip trip = tripRepository.find(id);
        return TripMapper.INSTANCE.mapTripDto(trip);
    }

    @Override
    public List<CarDto> findCarsByTripId(long tripId) {
        List<Car> cars = tripRepository.findCarsByTripId(tripId);
        return cars.stream()
                .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                .collect(Collectors.toList());
    }

    @Override
    public TripConfirmDto create(TripCreateDto tripCreateDto) {
        log.info("create new trip");
        Category category = categoryRepository.find(tripCreateDto.getCategoryId());
        List<CarDto> cars = new ArrayList<>();

        if (tripCreateDto.isMultipleCars()) {
            cars = carRepository.findCars(tripCreateDto.getCategoryId(), tripCreateDto.getCapacity())
                    .stream()
                    .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                    .collect(Collectors.toList());
            } else if (tripCreateDto.isIgnoreCategory()) {
            Car car = carRepository.findByCapacity(tripCreateDto.getCapacity());
            category = categoryRepository.find(car.getCategoryId());
            cars.add(CarMapper.INSTANCE.mapCarDto(car));
        } else {
            Car car = carRepository.find(tripCreateDto.getCategoryId(), tripCreateDto.getCapacity());
            cars.add(CarMapper.INSTANCE.mapCarDto(car));
        }
        BigDecimal distance = getDistanceForTrip(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
        BigDecimal price = getPrice(category.getPrice(), distance).multiply(BigDecimal.valueOf(cars.size()));
        BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
        BigDecimal total = price.subtract(discount);
        LocalTime waitTime = getWaitTime(tripCreateDto.getOriginId(), cars);
        return TripConfirmDto.builder()
                .personId(tripCreateDto.getPersonId())
                .originId(tripCreateDto.getOriginId())
                .destinationId(tripCreateDto.getDestinationId())
                .categoryId(category.getId())
                .distance(distance)
                .price(price).discount(discount)
                .total(total)
                .waitTime(waitTime)
                .cars(cars)
                .build();
    }

    @Override
    public TripDto confirm(TripConfirmDto tripConfirmDto) {
        log.info("confirm new trip");
        List<CarDto> carDtoList = tripConfirmDto.getCars();
        List<Car> cars = new ArrayList<>();

        for (CarDto carDto : carDtoList) {
            Car car = carRepository.find(carDto.getId());
            if (tripConfirmDto.getCategoryId() != car.getCategoryId()) {
                throw new RuntimeException("Car doesn't belong to category!");
            }
            cars.add(car);
        }
        Category category = categoryRepository.find(tripConfirmDto.getCategoryId());
        BigDecimal distance = getDistanceForTrip(tripConfirmDto.getOriginId(), tripConfirmDto.getDestinationId());
        BigDecimal price = getPrice(category.getPrice(), distance).multiply(BigDecimal.valueOf(cars.size()));
        BigDecimal discount = getDiscount(tripConfirmDto.getPersonId(), price);
        BigDecimal total = price.subtract(discount);
        Trip trip = Trip.builder()
                .personId(tripConfirmDto.getPersonId())
                .originId(tripConfirmDto.getOriginId())
                .destinationId(tripConfirmDto.getDestinationId())
                .distance(distance)
                .bill(total)
                .build();
        trip = tripRepository.create(trip, cars);
        return TripMapper.INSTANCE.mapTripDto(trip);
    }

    @Override
    public List<TripDto> getAll() {
        log.info("get all trips");
        return tripRepository.findAll()
                .stream().map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByPersonId(long personId) {
        log.info("get list of trips filtered by person id {}", personId);
        return tripRepository.findAllByPersonId(personId)
                .stream()
                .map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByDate(String dateRange) {
        log.info("get list of trips filtered by date range {}", dateRange);
        LocalDateTime[] date = getDateRange(dateRange);
        return tripRepository.findAllByDate(date)
                .stream()
                .map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByPersonIdAndDate(long personId, String dateRange) {
        log.info("get list of trips filtered by pereson id {} and date range {}", personId, dateRange);
        LocalDateTime[] date = getDateRange(dateRange);
        return tripRepository.findAllByPersonIdAndDate(personId, date)
                .stream()
                .map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
                .collect(Collectors.toList());
    }

    @Override
    public TripDto updateStatus(long tripId, String status) {
        log.info("update trip status to {} for trip with id {}", status, tripId);
        TripStatus tripStatus = TripStatus.valueOf(status.toUpperCase());
        Trip trip = tripRepository.updateStatus(tripId, tripStatus);
        return TripMapper.INSTANCE.mapTripDto(trip);
    }

    private BigDecimal getDistanceForTrip(long originId, long destinationId) {
        BigDecimal distance = getDistance(originId, destinationId);

        if (distance.compareTo(MIN_DICTANCE) < 0) {
            throw new RuntimeException("Distance is not enough!");

        }
        return distance;
    }

    private BigDecimal getDistance(long originId, long destinationId) {
        log.info("find distance beetween locations with id's {} and {}", originId, destinationId);
        Location origin = locationRepository.find(originId);
        Location destination = locationRepository.find(destinationId);
        BigDecimal lat1 = origin.getLatitude();
        BigDecimal lat2 = destination.getLatitude();
        BigDecimal lon1 = origin.getLongitude();
        BigDecimal lon2 = destination.getLongitude();
        BigDecimal distance = DistanceCalculator.getDistance(lat1, lat2, lon1, lon2);
        return distance.setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getPrice(BigDecimal categoryPrice, BigDecimal distance) {
        return categoryPrice.multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getDiscount(long personId, BigDecimal bill) {
        BigDecimal result = null;
        BigDecimal discount = null;
        BigDecimal totalBill = tripRepository.getTotalBill(personId, TripStatus.COMPLETED);
        discount = discounts.entrySet()
                .stream()
                .filter(entry -> entry.getKey().compareTo(totalBill) <= 0)
                .map(entry -> entry.getValue())
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        result = bill.multiply(discount).setScale(SCALE, RoundingMode.HALF_UP);
        return result;
    }

    private LocalTime getWaitTime(long originId, List<CarDto> cars) {
        List<BigDecimal> distanceToCar = cars.stream()
                .map(car -> getDistance(originId, car.getLocationId()))
                .collect(Collectors.toList());
        BigDecimal maxCarDistance = distanceToCar.stream()
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        int waitTime = maxCarDistance.divide(AVG_SPEED, SCALE, RoundingMode.HALF_UP).intValue();
        return LocalTime.MIN.plus(Duration.ofMinutes(waitTime));
    }

    private LocalDateTime[] getDateRange(String dateRange) {
        LocalDateTime[] result = new LocalDateTime[2];
        String[] range = dateRange.split("-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        result[0] = LocalDate.parse(range[0], formatter).atStartOfDay();
        result[1] = LocalDate.parse(range[1], formatter).atStartOfDay();
        return result;
    }
}

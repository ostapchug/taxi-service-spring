package com.example.taxiservicespring.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.example.taxiservicespring.service.exception.DataProcessingException;
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
    private static final BigDecimal MIN_DICTANCE = BigDecimal.ONE;
    private static final BigDecimal AVG_SPEED = BigDecimal.valueOf(0.3); // car average speed in km/min
    private static final int SCALE = 2;
    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final CarRepository carRepository;

    @Value("#{${discount}}")
    private final Map<BigDecimal, BigDecimal> discounts;

    @Override
    public TripDto getById(long id) {
        log.info("get trip by id {}", id);
        Trip trip = tripRepository.findById(id);
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
    public List<TripDto> getAllByDate(LocalDateTime[] dateRange) {
        log.info("get list of trips filtered by date range {} - {}", dateRange[0], dateRange[1]);
        return tripRepository.findAllByDate(dateRange)
                .stream()
                .map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange) {
        log.info("get list of trips filtered by person id {} and date range {} - {}", personId, dateRange[0],
                dateRange[1]);
        return tripRepository.findAllByPersonIdAndDate(personId, dateRange)
                .stream()
                .map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getCarsByTripId(long id) {
        List<Car> cars = tripRepository.findCarsByTripId(id);
        return cars.stream()
                .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                .collect(Collectors.toList());
    }

    @Override
    public TripConfirmDto create(TripCreateDto tripCreateDto) {
        log.info("create new trip");
        Category category = categoryRepository.findById(tripCreateDto.getCategoryId());
        List<CarDto> cars = new ArrayList<>();

        if (tripCreateDto.isMultipleCars()) {
            cars = carRepository
                    .findAllByCategoryAndCapacity(tripCreateDto.getCategoryId(), tripCreateDto.getCapacity())
                    .stream()
                    .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                    .collect(Collectors.toList());
        } else if (tripCreateDto.isIgnoreCategory()) {
            Car car = carRepository.findByCapacity(tripCreateDto.getCapacity());
            category = categoryRepository.findById(car.getCategoryId());
            cars.add(CarMapper.INSTANCE.mapCarDto(car));
        } else {
            Car car = carRepository.findByCategoryAndCapacity(tripCreateDto.getCategoryId(),
                    tripCreateDto.getCapacity());
            cars.add(CarMapper.INSTANCE.mapCarDto(car));
        }
        return createTripConfirmDto(tripCreateDto, category, cars);
    }

    @Override
    public TripDto confirm(TripConfirmDto tripConfirmDto) {
        log.info("confirm new trip");
        List<CarDto> carDtoList = tripConfirmDto.getCars();
        List<Car> cars = new ArrayList<>();

        for (CarDto carDto : carDtoList) {
            Car car = carRepository.findById(carDto.getId());
            if (tripConfirmDto.getCategoryId() != car.getCategoryId()) {
                throw new DataProcessingException("Car doesn't belong to category!");
            }
            cars.add(car);
        }
        Trip trip = createTrip(tripConfirmDto);
        trip = tripRepository.create(trip, cars);
        return TripMapper.INSTANCE.mapTripDto(trip);
    }

    @Override
    public TripDto updateStatus(long id, TripStatus status) {
        log.info("update trip status to {} for trip with id {}", status, id);
        Trip trip = tripRepository.updateStatus(id, status);
        return TripMapper.INSTANCE.mapTripDto(trip);
    }

    private TripConfirmDto createTripConfirmDto(TripCreateDto tripCreateDto, Category category, List<CarDto> cars) {
        BigDecimal distance = getDistanceForTrip(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
        BigDecimal price = getPrice(category.getPrice(), distance, cars.size());
        BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
        BigDecimal bill = price.subtract(discount);
        LocalTime waitTime = getWaitTime(tripCreateDto.getOriginId(), cars);
        return TripConfirmDto.builder()
                .personId(tripCreateDto.getPersonId())
                .originId(tripCreateDto.getOriginId())
                .destinationId(tripCreateDto.getDestinationId())
                .categoryId(tripCreateDto.getCategoryId())
                .distance(distance)
                .price(price).discount(discount)
                .total(bill)
                .waitTime(waitTime)
                .cars(cars)
                .build();
    }

    private Trip createTrip(TripConfirmDto tripConfirmDto) {
        Category category = categoryRepository.findById(tripConfirmDto.getCategoryId());
        BigDecimal distance = getDistanceForTrip(tripConfirmDto.getOriginId(), tripConfirmDto.getDestinationId());
        BigDecimal price = getPrice(category.getPrice(), distance, tripConfirmDto.getCars().size());
        BigDecimal discount = getDiscount(tripConfirmDto.getPersonId(), price);
        BigDecimal bill = price.subtract(discount);
        return Trip.builder()
                .personId(tripConfirmDto.getPersonId())
                .originId(tripConfirmDto.getOriginId())
                .destinationId(tripConfirmDto.getDestinationId())
                .distance(distance)
                .bill(bill)
                .build();
    }

    private BigDecimal getDistance(long originId, long destinationId) {
        log.info("find distance beetween locations with id's {} and {}", originId, destinationId);
        Location origin = locationRepository.findById(originId);
        Location destination = locationRepository.findById(destinationId);
        BigDecimal distance = DistanceCalculator.getDistance(origin.getLatitude(), destination.getLatitude(),
                origin.getLongitude(), destination.getLongitude());
        return distance.setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getDistanceForTrip(long originId, long destinationId) {
        BigDecimal distance = getDistance(originId, destinationId);

        if (distance.compareTo(MIN_DICTANCE) < 0) {
            throw new DataProcessingException("Distance is not enough!");
        }
        return distance;
    }

    private BigDecimal getPrice(BigDecimal categoryPrice, BigDecimal distance, int carCount) {
        return categoryPrice.multiply(distance)
                .multiply(BigDecimal.valueOf(carCount))
                .setScale(SCALE, RoundingMode.HALF_UP);
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
}

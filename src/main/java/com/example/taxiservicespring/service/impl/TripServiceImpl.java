package com.example.taxiservicespring.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.TripService;
import com.example.taxiservicespring.service.exception.DataProcessingException;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.mapper.CarMapper;
import com.example.taxiservicespring.service.mapper.TripMapper;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarStatus;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.model.Location;

import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.service.repository.CategoryRepository;
import com.example.taxiservicespring.service.repository.LocationRepository;
import com.example.taxiservicespring.service.repository.PersonRepository;
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
    private final PersonRepository personRepository;
    private final TripMapper tripMapper;

    @Value("#{${discount}}")
    private final Map<BigDecimal, BigDecimal> discounts;

    @Override
    public TripDto getById(long id) {
        log.info("get trip by id {}", id);
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip is not found!"));
        return tripMapper.mapTripDto(trip);
    }

    @Override
    public Page<TripDto> getAll(Pageable pageable) {
        log.info("get all trips");
        Page<Trip> trips = tripRepository.findAll(pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public Page<TripDto> getAllByPersonId(long personId, Pageable pageable) {
        log.info("get list of trips filtered by person id {}", personId);
        Page<Trip> trips = tripRepository.findAllByPersonId(personId, pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public Page<TripDto> getAllByDate(LocalDateTime[] dateRange, Pageable pageable) {
        log.info("get list of trips filtered by date range {} - {}", dateRange[0], dateRange[1]);
        Page<Trip> trips = tripRepository.findAllByDateBetween(dateRange[0], dateRange[1], pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public Page<TripDto> getAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange, Pageable pageable) {
        log.info("get list of trips filtered by pereson id {} and date range {} - {}", personId, dateRange[0],
                dateRange[1]);
        Page<Trip> trips = tripRepository.findAllByPersonIdAndDateBetween(personId, dateRange[0], dateRange[1],
                pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public TripConfirmDto create(TripCreateDto tripCreateDto) {
        log.info("create new trip");
        Category category = categoryRepository.getReferenceById(tripCreateDto.getCategoryId());
        List<CarDto> cars = new ArrayList<>();

        if (tripCreateDto.isMultipleCars()) {
            cars = getMultipleCars(tripCreateDto.getCategoryId(), tripCreateDto.getCapacity());
        } else if (tripCreateDto.isIgnoreCategory()) {
            Car car = carRepository.findByStatusAndCapacity(CarStatus.READY, tripCreateDto.getCapacity())
                    .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));
            category = categoryRepository.getReferenceById(car.getCategory().getId());
            cars.add(CarMapper.INSTANCE.mapCarDto(car));
        } else {
            Car car = carRepository.findByCategoryIdAndStatusAndCapacity(
                    tripCreateDto.getCategoryId(), CarStatus.READY, tripCreateDto.getCapacity())
                    .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));
            cars.add(CarMapper.INSTANCE.mapCarDto(car));
        }
        return createTripConfirmDto(tripCreateDto, category, cars);
    }

    @Transactional
    @Override
    public TripDto confirm(TripConfirmDto tripConfirmDto) {
        log.info("confirm new trip");
        List<CarDto> carDtoList = tripConfirmDto.getCars();
        List<Car> cars = new ArrayList<>();

        for (CarDto carDto : carDtoList) {
            Car car = carRepository.findByIdForUpdate(carDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));

            if (tripConfirmDto.getCategoryId() != car.getCategory().getId()) {
                throw new DataProcessingException("Car doesn't belong to category");
            }

            if (!car.getStatus().equals(CarStatus.READY)) {
                throw new DataProcessingException("Can't create new trip, car is busy");
            }
            car.setStatus(CarStatus.BUSY);
            cars.add(car);
        }
        Trip trip = createTrip(tripConfirmDto);
        trip.getCars().addAll(cars);
        trip = tripRepository.save(trip);
        return tripMapper.mapTripDto(trip);
    }

    @Transactional
    @Override
    public TripDto updateStatus(long id, TripStatus status) {
        log.info("update trip status to {} for trip with id {}", status, id);
        Trip trip = tripRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip is not found!"));
        TripStatus currentStatus = trip.getStatus();
        if (currentStatus == TripStatus.NEW || currentStatus == TripStatus.ACCEPTED) {
            trip.setStatus(status);

            if (status == TripStatus.COMPLETED || status == TripStatus.CANCELLED) {
                Set<Car> cars = trip.getCars();
                for (Car car : cars) {
                    car.setStatus(CarStatus.READY);
                }
            }
        } else {
            throw new DataProcessingException("Can't change status for trip that already done");
        }
        trip = tripRepository.save(trip);
        return tripMapper.mapTripDto(trip);
    }

    private TripConfirmDto createTripConfirmDto(TripCreateDto tripCreateDto, Category category, List<CarDto> cars) {
        BigDecimal distance = getDistanceForTrip(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
        BigDecimal price = getPrice(category.getId(), distance, cars.size());
        BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
        BigDecimal total = price.subtract(discount);
        LocalTime waitTime = getWaitTime(tripCreateDto.getOriginId(), cars);
        return TripConfirmDto.builder()
                .personId(tripCreateDto.getPersonId())
                .originId(tripCreateDto.getOriginId())
                .destinationId(tripCreateDto.getDestinationId())
                .categoryId(tripCreateDto.getCategoryId())
                .distance(distance)
                .price(price).discount(discount)
                .total(total)
                .waitTime(waitTime)
                .cars(cars)
                .build();
    }

    private Trip createTrip(TripConfirmDto tripConfirmDto) {
        Person person = personRepository.getReferenceById(tripConfirmDto.getPersonId());
        Location origin = locationRepository.getReferenceById(tripConfirmDto.getOriginId());
        Location destination = locationRepository.getReferenceById(tripConfirmDto.getDestinationId());
        Category category = categoryRepository.getReferenceById(tripConfirmDto.getCategoryId());
        BigDecimal distance = getDistanceForTrip(tripConfirmDto.getOriginId(), tripConfirmDto.getDestinationId());
        BigDecimal price = getPrice(category.getId(), distance, tripConfirmDto.getCars().size());
        BigDecimal discount = getDiscount(tripConfirmDto.getPersonId(), price);
        BigDecimal total = price.subtract(discount);
        return Trip.builder()
                .person(person)
                .origin(origin)
                .destination(destination)
                .distance(distance)
                .bill(total)
                .date(LocalDateTime.now())
                .build();
    }

    private BigDecimal getDistance(long originId, long destinationId) {
        log.info("find distance beetween locations with id's {} and {}", originId, destinationId);
        Location origin = locationRepository.findById(originId)
                .orElseThrow(() -> new EntityNotFoundException("Origin location is not found!"));
        Location destination = locationRepository.findById(destinationId)
                .orElseThrow(() -> new EntityNotFoundException("Destination location is not found!"));
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

    private List<CarDto> getMultipleCars(int categoryId, int capacity) {
        List<Car> result = new ArrayList<>();
        List<Car> cars = carRepository.findAllByCategoryIdAndStatus(categoryId, CarStatus.READY);
        cars.sort((c1, c2) -> c2.getModel().getSeatCount() - c1.getModel().getSeatCount());

        for (Car car : cars) {
            int currentCapacity = car.getModel().getSeatCount();
            if (capacity >= currentCapacity) {
                result.add(car);
                capacity -= currentCapacity;
            }

            if (capacity == 0) {
                break;
            }
        }
        cars.removeAll(result);

        if (capacity > 0 && cars.size() > 0) {
            capacity -= cars.get(cars.size() - 1).getModel().getSeatCount();
            result.add(cars.get(cars.size() - 1));
        }

        if (capacity > 0) {
            throw new DataProcessingException("Not enough cars in this category");
        }
        return result.stream()
                .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                .collect(Collectors.toList());
    }

    private BigDecimal getPrice(int categoryId, BigDecimal distance, int carCount) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category is not found!"));
        return category.getPrice().multiply(distance)
                .multiply(BigDecimal.valueOf(carCount))
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getDiscount(long personId, BigDecimal bill) {
        BigDecimal result = null;
        BigDecimal discount = null;
        BigDecimal totalBill = tripRepository.getTotalBill(personId, TripStatus.COMPLETED)
                .orElse(BigDecimal.ZERO);
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

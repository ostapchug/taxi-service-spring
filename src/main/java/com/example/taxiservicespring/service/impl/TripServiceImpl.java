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
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private static final String DATE_FORMAT = "dd.MM.yy";
    private static final BigDecimal MIN_DICTANCE = BigDecimal.valueOf(1);
    private static final BigDecimal AVG_SPEED = BigDecimal.valueOf(0.3); // car average speed in km/min
    private static final int SCALE = 2;
    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final TripMapper tripMapper;  

    @Override
    public TripDto find(long id) {
        log.info("get trip by id {}", id);
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip is not found!"));
        return tripMapper.mapTripDto(trip);
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
        
        BigDecimal distance = getDistanceForTrip(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
        BigDecimal price = getPrice(category.getId(), distance).multiply(BigDecimal.valueOf(cars.size()));
        BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
        BigDecimal total = price.subtract(discount);
        LocalTime waitTime = getWaitTime(tripCreateDto.getOriginId(), cars);
        return TripConfirmDto.builder()
                .personId(tripCreateDto.getPersonId())
                .originId(tripCreateDto.getOriginId())
                .destinationId(tripCreateDto.getDestinationId())
                .categoryId(category.getId())
                .distance(distance)
                .price(price)
                .discount(discount)
                .total(total)
                .waitTime(waitTime)
                .cars(cars)
                .build();
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
                log.error("DataProcessingException: message car doesn't belong to category");
                throw new DataProcessingException("Car doesn't belong to category");
            }
            
            if(!car.getStatus().equals(CarStatus.READY)) {
                log.error("DataProcessingException: message can't create new trip, car is busy");
                throw new DataProcessingException("Can't create new trip, car is busy");
            }
            car.setStatus(CarStatus.BUSY);
            cars.add(car);
        }
        
        Person person = personRepository.getReferenceById(tripConfirmDto.getPersonId());
        Location origin = locationRepository.getReferenceById(tripConfirmDto.getOriginId());
        Location destination = locationRepository.getReferenceById(tripConfirmDto.getDestinationId());
        Category category = categoryRepository.getReferenceById(tripConfirmDto.getCategoryId());
        BigDecimal distance = getDistanceForTrip(tripConfirmDto.getOriginId(), tripConfirmDto.getDestinationId());        
        BigDecimal price = getPrice(category.getId(), distance).multiply(BigDecimal.valueOf(cars.size()));
        BigDecimal discount = getDiscount(tripConfirmDto.getPersonId(), price);
        BigDecimal total = price.subtract(discount);
        Trip trip = Trip.builder()
                .person(person)
                .origin(origin)
                .destination(destination)
                .distance(distance)
                .bill(total)
                .date(LocalDateTime.now())
                .build();
        trip.getCars().addAll(cars);
        trip = tripRepository.save(trip);
        return tripMapper.mapTripDto(trip);
    }
    
    private BigDecimal getDistanceForTrip(long originId, long destinationId) {
        BigDecimal distance = getDistance(originId, destinationId);
        
        if (distance.compareTo(MIN_DICTANCE) < 0) {
            throw new DataProcessingException("Distance is not enough!");

        } 
        return distance; 
    }

    private BigDecimal getDistance(long originId, long destinationId) {  
        log.info("find distance beetween locations with id's {} and {}", originId, destinationId);
        Location origin = locationRepository.findById(originId)
                .orElseThrow(() -> new EntityNotFoundException("Origin location is not found!"));
        Location destination = locationRepository.findById(destinationId)
                .orElseThrow(() -> new EntityNotFoundException("Destination location is not found!"));
        double r = 6371;
        double lat1 = Math.toRadians(origin.getLatitude().doubleValue());
        double lat2 = Math.toRadians(destination.getLatitude().doubleValue());
        double lon1 = Math.toRadians(origin.getLongitude().doubleValue());
        double lon2 = Math.toRadians(destination.getLongitude().doubleValue());
        
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return BigDecimal.valueOf(c * r).setScale(SCALE, RoundingMode.HALF_UP);
    }
    
    @Override
    public Page<TripDto> getAll(int page, int count, String sorting) {
        log.info("get all trips");
        Pageable pageable = PageRequest.of(--page, count, getSorting(sorting));
        Page<Trip> trips = tripRepository.findAll(pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public Page<TripDto> getAllByPersonId(long personId, int page, int count, String sorting) {
        log.info("get list of trips filtered by person id {}", personId);
        Pageable pageable = PageRequest.of(--page, count, getSorting(sorting));
        Page<Trip> trips = tripRepository.findAllByPersonId(personId, pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public Page<TripDto> getAllByDate(String dateRange, int page, int count, String sorting) {
        log.info("get list of trips filtered by date range {}", dateRange);
        LocalDateTime[] date = getDateRange(dateRange);
        Pageable pageable = PageRequest.of(--page, count, getSorting(sorting));
        Page<Trip> trips = tripRepository.findAllByDateBetween(date[0], date[1], pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }

    @Override
    public Page<TripDto> getAllByPersonIdAndDate(long personId, String dateRange, int page, int count, String sorting) {
        log.info("get list of trips filtered by pereson id {} and date range {}", personId, dateRange);
        LocalDateTime[] date = getDateRange(dateRange);
        Pageable pageable = PageRequest.of(--page, count, getSorting(sorting));
        Page<Trip> trips = tripRepository.findAllByPersonIdAndDateBetween(personId, date[0], date[1], pageable);
        return new PageImpl<>(
                trips.getContent()
                .stream()
                .map(trip -> tripMapper.mapTripDto(trip))
                .collect(Collectors.toList()), pageable, trips.getTotalElements());
    }
    
    @Transactional
    @Override
    public TripDto updateStatus(long tripId, String status) {
        log.info("update trip status for trip with id {}", tripId);
        Trip trip = tripRepository.findByIdForUpdate(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip is not found!"));
        TripStatus tripStatus = trip.getStatus();
        TripStatus newTripStatus = TripStatus.valueOf(status.toUpperCase());
        
        if(tripStatus.equals(TripStatus.NEW) || tripStatus.equals(TripStatus.ACCEPTED)) {
            trip.setStatus(newTripStatus);
            
            if(newTripStatus.equals(TripStatus.COMPLETED) || newTripStatus.equals(TripStatus.CANCELLED)) {
                Set<Car> cars = trip.getCars();        
                for(Car car : cars) {
                    car.setStatus(CarStatus.READY);
                }
            }
        }else {
            log.error("DataProcessingException: message can't change status for trip that already done");
            throw new DataProcessingException("Can't change status for trip that already done");
        }
        
        trip = tripRepository.save(trip);
        return tripMapper.mapTripDto(trip);
    }
    
    private List<CarDto> getMultipleCars(int categoryId, int capacity) {
        List<Car> result = new ArrayList<>();
        List<Car> cars = carRepository.findAllByCategoryIdAndStatus(categoryId, CarStatus.READY);
        cars.sort((c1, c2) -> c2.getModel().getSeatCount() - c1.getModel().getSeatCount());
        
        int i = 3;
        while(cars.size() != 0 && capacity > 0 && i > 0) {
            for (int j = 0; j < cars.size(); j++) {
                int currentCapacity = cars.get(j).getModel().getSeatCount();
                if (capacity - currentCapacity >= -currentCapacity / i) {
                    result.add(cars.remove(j));
                    capacity -= currentCapacity;
                }
            }
            i--;
        }
        
        if (capacity > 0) {
            log.error("DataProcessingException: message not enough cars in this category");
            throw new DataProcessingException("Not enough cars in this category");
        }
        return result.stream()
                .map(car -> CarMapper.INSTANCE.mapCarDto(car))
                .collect(Collectors.toList());
    }

    private BigDecimal getPrice(int categoryId, BigDecimal distance) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category is not found!"));
        return category.getPrice().multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getDiscount(long personId, BigDecimal bill) {
        BigDecimal result = null;
        BigDecimal totalBill = tripRepository.getTotalBill(personId, TripStatus.COMPLETED);

        if (totalBill == null) {
            totalBill = BigDecimal.ZERO;
        }

        if (totalBill.compareTo(BigDecimal.valueOf(100)) >= 0) {
            result = bill.multiply(BigDecimal.valueOf(0.02));
        } else if (totalBill.compareTo(BigDecimal.valueOf(500)) >= 0) {
            result = bill.multiply(BigDecimal.valueOf(0.05));
        } else if (totalBill.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            result = bill.multiply(BigDecimal.valueOf(0.10));
        } else {
            result = BigDecimal.ZERO;
        }
        return result.setScale(SCALE, RoundingMode.HALF_UP);
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
    
    private Sort getSorting(String sorting) {
        Sort result = null;
        
        switch (sorting) {
        case "date-asc":
            result = Sort.by("date");
            break;
        case "bill-asc":
            result = Sort.by("bill");
            break;
        case "bill-desc":
            result = Sort.by("bill").descending();
            break;
        default:
            result = Sort.by("date").descending();
            break;
        }  
        return result;
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

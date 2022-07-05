package com.example.taxiservicespring.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.service.repository.CategoryRepository;
import com.example.taxiservicespring.service.repository.LocationRepository;
import com.example.taxiservicespring.service.repository.TripRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private static final BigDecimal AVG_SPEED = new BigDecimal(0.3); // car average speed in km/min
    private static final int SCALE = 2;
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
	TripConfirmDto result = null;
	
	if (tripCreateDto.isMultipleCars()) {
	    result = setTripConfirmDtoMultipleCars(tripCreateDto);
	} else if (tripCreateDto.isIgnoreCategory()) {
	    result = setTripConfirmDtoIgnoreCategory(tripCreateDto);
	} else {
	    result = setTripConfirmDto(tripCreateDto);
	}
	
	return result;
    }

    @Override
    public TripDto confirm(TripConfirmDto tripConfirmDto) {
	log.info("confirm new trip");
	Trip trip = setTrip(tripConfirmDto);
	trip = tripRepository.create(trip, tripConfirmDto.getCars());
	return TripMapper.INSTANCE.mapTripDto(trip);
    }

    @Override
    public List<TripDto> getAll(int page, int count, String sorting) {
	log.info("get all trips");
	int offset = (page - 1) * count;
	return tripRepository.findAll(offset, count, sorting)
		.stream()
		.map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
		.collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByPersonId(long personId, int page, int count, String sorting) {
	log.info("get list of trips filtered by person id {}", personId);
	int offset = (page - 1) * count;
	return tripRepository.findAllByPersonId(personId, offset, count, sorting)
		.stream()
		.map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
		.collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByDate(String dateRange, int page, int count, String sorting) {
	log.info("get list of trips filtered by date range {}", dateRange);
	int offset = (page - 1) * count;
	Timestamp[] date = getDateRange(dateRange);
	return tripRepository.findAllByDate(date, offset, count, sorting)
		.stream()
		.map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
		.collect(Collectors.toList());
    }

    @Override
    public List<TripDto> getAllByPersonIdAndDate(long personId, String dateRange, int page, int count, String sorting) {
	log.info("get list of trips filtered by pereson id {} and date range {}", personId, dateRange);
	int offset = (page - 1) * count;
	Timestamp[] date = getDateRange(dateRange);
	return tripRepository.findAllByPersonIdAndDate(personId, date, offset, count, sorting)
		.stream()
		.map(trip -> TripMapper.INSTANCE.mapTripDto(trip))
		.collect(Collectors.toList());
    }

    @Override
    public long getCount() {
	log.info("get all trips count");
	return tripRepository.getCount();
    }

    @Override
    public long getCountByPersonId(long personId) {
	log.info("get count of trips filtered by person id {}", personId);
	return tripRepository.getCountByPersonId(personId);
    }

    @Override
    public long getCountByDate(String dateRange) {
	log.info("get count of trips filtered by date {}", dateRange);
	Timestamp[] date = getDateRange(dateRange);
	return tripRepository.getCountByDate(date);
    }

    @Override
    public long getCountByPersonIdAndDate(long personId, String dateRange) {
	log.info("get count of trips filtered by person id {} and date {}", personId, dateRange);
	Timestamp[] date = getDateRange(dateRange);
	return tripRepository.getCountByPersonIdAndDate(personId, date);
    }

    @Override
    public TripDto updateStatus(long tripId, String status) {
	log.info("update trip status for trip with id {}", tripId);
	int statusId = TripStatus.getId(status);
	Trip trip = tripRepository.updateStatus(tripId, statusId);
	return TripMapper.INSTANCE.mapTripDto(trip);
    }

    private Trip setTrip(TripConfirmDto tripConfirmDto) {
	BigDecimal distance = getDistance(tripConfirmDto.getOriginId(), tripConfirmDto.getDestinationId());
	Category category = categoryRepository.find(tripConfirmDto.getCategoryId());
	List<Car> cars = tripConfirmDto.getCars();

	for (Car car : cars) {
	    Car dbCar = carRepository.find(car.getId());
	    if (tripConfirmDto.getCategoryId() != dbCar.getCategoryId()) {
		throw new RuntimeException("Can't create trip");
	    }
	}

	BigDecimal price = getPrice(category.getPrice(), distance).multiply(new BigDecimal(cars.size()));
	BigDecimal discount = getDiscount(tripConfirmDto.getPersonId(), price);
	BigDecimal total = price.subtract(discount);

	return Trip.builder()
		.personId(tripConfirmDto.getPersonId())
		.originId(tripConfirmDto.getOriginId())
		.destinationId(tripConfirmDto.getDestinationId())
		.distance(distance)
		.bill(total)
		.build();
    }

    private TripConfirmDto setTripConfirmDto(TripCreateDto tripCreateDto) {
	BigDecimal distance = getDistance(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
	Category category = categoryRepository.find(tripCreateDto.getCategoryId());
	Car car = carRepository.find(tripCreateDto.getCategoryId(), tripCreateDto.getCapacity());
	
	BigDecimal price = getPrice(category.getPrice(), distance);
	BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
	BigDecimal total = price.subtract(discount);
	
	int waitTime = getWaitTime(locationRepository.findDistance(tripCreateDto.getOriginId(), car.getLocationId()));
	return TripConfirmDto.builder()
		.personId(tripCreateDto.getPersonId())
		.originId(tripCreateDto.getOriginId())
		.destinationId(tripCreateDto.getDestinationId())
		.categoryId(category.getId())
		.distance(distance)
		.price(price).discount(discount)
		.total(total).waitTime(waitTime)
		.cars(new ArrayList<>(List.of(car)))
		.build();
    }

    private TripConfirmDto setTripConfirmDtoMultipleCars(TripCreateDto tripCreateDto) {
	BigDecimal distance = getDistance(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
	Category category = categoryRepository.find(tripCreateDto.getCategoryId());
	List<Car> cars = carRepository.findCars(tripCreateDto.getCategoryId(), tripCreateDto.getCapacity());
	
	BigDecimal price = getPrice(category.getPrice(), distance).multiply(new BigDecimal(cars.size()));
	BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
	BigDecimal total = price.subtract(discount);
	List<BigDecimal> carDistance = new ArrayList<>();
	
	for (Car car : cars) {
	    carDistance.add(locationRepository.findDistance(tripCreateDto.getOriginId(), car.getLocationId()));
	}
	
	BigDecimal minCarDistance = carDistance
		.stream()
		.min(Comparator.naturalOrder())
		.orElse(new BigDecimal(0));
	int waitTime = getWaitTime(minCarDistance);
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

    private TripConfirmDto setTripConfirmDtoIgnoreCategory(TripCreateDto tripCreateDto) {
	BigDecimal distance = getDistance(tripCreateDto.getOriginId(), tripCreateDto.getDestinationId());
	Car car = carRepository.findByCapacity(tripCreateDto.getCapacity());
	Category category = categoryRepository.find(car.getCategoryId());
	
	BigDecimal price = getPrice(category.getPrice(), distance);
	BigDecimal discount = getDiscount(tripCreateDto.getPersonId(), price);
	BigDecimal total = price.subtract(discount);
	
	int waitTime = getWaitTime(locationRepository.findDistance(tripCreateDto.getOriginId(), car.getLocationId()));
	return TripConfirmDto.builder()
		.personId(tripCreateDto.getPersonId())
		.originId(tripCreateDto.getOriginId())
		.destinationId(tripCreateDto.getDestinationId())
		.categoryId(category.getId())
		.distance(distance)
		.price(price).discount(discount)
		.total(total)
		.waitTime(waitTime)
		.cars(new ArrayList<>(List.of(car)))
		.build();
    }

    private BigDecimal getDistance(long originId, long destinationId) {
	return locationRepository.findDistance(originId, destinationId).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getPrice(BigDecimal categoryPrice, BigDecimal distance) {
	return categoryPrice.multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal getDiscount(long personId, BigDecimal bill) {
	BigDecimal result = null;
	BigDecimal totalBill = tripRepository.getTotalBill(personId);

	if (totalBill == null) {
	    totalBill = new BigDecimal(0);
	}

	if (totalBill.compareTo(new BigDecimal(100)) >= 0) {
	    result = bill.multiply(new BigDecimal(0.02));
	} else if (totalBill.compareTo(new BigDecimal(500)) >= 0) {
	    result = bill.multiply(new BigDecimal(0.05));
	} else if (totalBill.compareTo(new BigDecimal(1000)) >= 0) {
	    result = bill.multiply(new BigDecimal(0.10));
	} else {
	    result = new BigDecimal(0);
	}
	
	return result.setScale(SCALE, RoundingMode.HALF_UP);
    }

    private int getWaitTime(BigDecimal distance) {
	return distance.divide(AVG_SPEED, SCALE, RoundingMode.HALF_UP).intValue();
    }

    private Timestamp[] getDateRange(String dateRange) {
	Timestamp[] result = new Timestamp[2];
	Date start = null;
	Date end = null;
	String[] range = dateRange.split("-");

	try {
	    start = new SimpleDateFormat("dd.MM.yy").parse(range[0]);
	    end = new SimpleDateFormat("dd.MM.yy").parse(range[1]);
	} catch (ParseException e) {
	    log.error("ParseException: message {}", e.getMessage(), e);
	    throw new RuntimeException("Date format is not valid!");
	}

	result[0] = new Timestamp(start.getTime());
	result[1] = new Timestamp(end.getTime());
	return result;
    }
}

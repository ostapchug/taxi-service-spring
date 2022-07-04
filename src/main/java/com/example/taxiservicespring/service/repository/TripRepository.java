package com.example.taxiservicespring.service.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.Trip;

public interface TripRepository {
	
	Trip find(long id);
	
	Trip create(Trip trip, List<Car> cars);
	
	List<Trip> findAll(int offset, int count, String sorting);
	
	List<Trip> findAllByPersonId(long personId, int offset, int count, String sorting);
	
	List<Trip> findAllByDate(Timestamp [] dateRange, int offset, int count, String sorting);
	
	List<Trip> findAllByPersonIdAndDate(long personId, Timestamp [] dateRange, int offset, int count, String sorting);
	
	List<Car> findCarsByTripId(long tripId);
	
	long getCount();
	
	long getCountByPersonId(long personId);
	
	long getCountByDate(Timestamp [] dateRange);
	
	long getCountByPersonIdAndDate(long personId, Timestamp [] dateRange);
	
	Trip updateStatus(long tripId, int statusId);
	
	BigDecimal getTotalBill(long personId);

}

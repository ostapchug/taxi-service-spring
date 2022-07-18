package com.example.taxiservicespring.service.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarStatus;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Car c WHERE c.id = ?1")
    Optional<Car> findByIdForUpdate(long id);

    Optional<Car> findByCategoryIdAndStatusAndCapacity(int categoryId, CarStatus status, int capacity);

    @Query(value = "SELECT * FROM car c INNER JOIN car_model cm ON c.model_id = cm.id WHERE "
            + "c.status_id = ?1 AND cm.seat_count >= ?2 ORDER BY cm.seat_count LIMIT 1", nativeQuery = true)
    Optional<Car> findByStatusAndCapacity(CarStatus status, int capacity);

    List<Car> findAllByCategoryIdAndStatus(int categoryId, CarStatus status);
}

package com.example.taxiservicespring.service.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Trip t WHERE t.id = ?1")
    Optional<Trip> findByIdForUpdate(long id);

    Page<Trip> findAllByPersonId(long personId, Pageable pageable);

    Page<Trip> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Trip> findAllByPersonIdAndDateBetween(long personId, LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable);
    
    @Query("SELECT SUM(t.bill) FROM Trip t WHERE t.person.id = :personId AND t.status = :status")
    Optional<BigDecimal> getTotalBill(@Param("personId") long personId, @Param("status") TripStatus status);
}

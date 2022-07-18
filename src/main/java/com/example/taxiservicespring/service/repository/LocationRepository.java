package com.example.taxiservicespring.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taxiservicespring.service.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}

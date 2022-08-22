package com.example.taxiservicespring.service.repository.impl;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.repository.CarModelRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcCarModelRepository implements CarModelRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<CarModel> findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM car_model WHERE id = ?",
                (rs, rowNum) ->  Optional.of(
                        new CarModel(
                                rs.getLong("id"),
                                rs.getString("brand"),
                                rs.getString("name"),
                                rs.getString("color"),
                                rs.getInt("year"),
                                rs.getInt("seat_count")
                                )
                        ),
                id
                );
    }
}

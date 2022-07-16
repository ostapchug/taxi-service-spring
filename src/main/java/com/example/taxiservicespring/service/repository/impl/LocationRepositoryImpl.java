package com.example.taxiservicespring.service.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.repository.LocationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocationRepositoryImpl implements LocationRepository {
    private final List<Location> locations = new ArrayList<>();

    LocationRepositoryImpl() {
        Location location = new Location();
        location.setId(1L);
        location.setStreetName("Molodizhna");
        location.setStreetNumber("36");
        location.setLatitude(BigDecimal.valueOf(48.925541084296924));
        location.setLongitude(BigDecimal.valueOf(24.737374909778257));
        locations.add(location);

        location = new Location();
        location.setId(2L);
        location.setStreetName("Nezalezhnosti");
        location.setStreetNumber("168");
        location.setLatitude(BigDecimal.valueOf(48.91574609793521));
        location.setLongitude(BigDecimal.valueOf(24.73589966428934));
        locations.add(location);

        location = new Location();
        location.setId(3L);
        location.setStreetName("Halytska");
        location.setStreetNumber("11");
        location.setLatitude(BigDecimal.valueOf(48.922336888655614));
        location.setLongitude(BigDecimal.valueOf(24.709438734753363));
        locations.add(location);

        location = new Location();
        location.setId(4L);
        location.setStreetName("Vovchynetska");
        location.setStreetNumber("225");
        location.setLatitude(BigDecimal.valueOf(48.94107697890707));
        location.setLongitude(BigDecimal.valueOf(24.73865635966828));
        locations.add(location);

        location = new Location();
        location.setId(5L);
        location.setStreetName("Pasichna");
        location.setStreetNumber("21");
        location.setLatitude(BigDecimal.valueOf(48.939417866685424));
        location.setLongitude(BigDecimal.valueOf(24.695695996555884));
        locations.add(location);
    }

    @Override
    public Location find(long id) {
        log.info("find location by id {}", id);
        return locations.stream()
                .filter(location -> location.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Location is not found!"));
    }

    @Override
    public List<Location> getAll() {
        log.info("find all locations");
        return new ArrayList<>(locations);
    }
}

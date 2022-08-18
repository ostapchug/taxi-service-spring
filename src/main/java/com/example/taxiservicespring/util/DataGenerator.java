package com.example.taxiservicespring.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.model.Role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataGenerator {
    private static final String [] LOCATION__STREET_NAMES = {"Molodizhna", "Nezalezhnosti", "Halytska", "Vovchynetska", "Pasichna"};
    private static final String [] LOCATION__STREET_NUMBERS = {"36", "168", "11", "225", "21"};
    private static final BigDecimal [] LOCATION__LATITUDES = {BigDecimal.valueOf(48.925541084296924), BigDecimal.valueOf(48.91574609793521), 
            BigDecimal.valueOf(48.922336888655614), BigDecimal.valueOf(48.94107697890707), BigDecimal.valueOf(48.939417866685424)};
    private static final BigDecimal [] LOCATION__LONGITUDES = {BigDecimal.valueOf(24.737374909778257), BigDecimal.valueOf(24.73589966428934), 
            BigDecimal.valueOf(24.709438734753363), BigDecimal.valueOf(24.73865635966828), BigDecimal.valueOf(24.695695996555884)};
    private static final String [] CATEGORY__NAMES = {"Economy", "Standard"};
    private static final BigDecimal [] CATEGORY__PRICES = {BigDecimal.valueOf(25), BigDecimal.valueOf(40)};
    private static final String CAR__BRAND = "Ford";
    private static final String [] CAR__NAMES = {"Focus", "Fusion"};
    private static final int [] CAR__CAPACITIES = {3, 4};
    private static final int [] CAR__YEARS = {2012, 2016};
    private static final String CAR__COLOR = "Blue";
    private static final String [] CAR__REG_NUMBERS = {"AA1234TV", "AA1235TV", "AA1236TV", "AA1237TV"};
    
    public static List<CarModel> createCarModels() {
        return IntStream.iterate(0, i -> i+1)
                .limit(2)
                .mapToObj(i -> CarModel.builder()
                        .id(i+1)
                        .brand(CAR__BRAND)
                        .name(CAR__NAMES[i])
                        .year(CAR__YEARS[i])
                        .color(CAR__COLOR)
                        .seatCount(CAR__CAPACITIES[i])
                        .build())
                .collect(Collectors.toList());
    }
    
    public static List<Category> createCategories() {
        return IntStream.iterate(0, i -> i+1)
                .limit(2)
                .mapToObj(i -> Category.builder()
                        .id(i+1)
                        .name(CATEGORY__NAMES[i])
                        .price(CATEGORY__PRICES[i])
                        .build())
                .collect(Collectors.toList());
    }
    
    public static List<Location> createLocations() {
        return IntStream.iterate(0, i -> i+1)
                .limit(4)
                .mapToObj(i -> Location.builder()
                        .id(i+1)
                        .streetName(LOCATION__STREET_NAMES[i])
                        .streetNumber(LOCATION__STREET_NUMBERS[i])
                        .latitude(LOCATION__LATITUDES[i])
                        .longitude(LOCATION__LONGITUDES[i])
                        .build())
                .collect(Collectors.toList());
    }
    
    public static List<Car> createCars() {
        List<CarModel> carModels = createCarModels();
        List<Category> categories = createCategories();
        List<Location> locations = createLocations();
        return IntStream.iterate(0, i -> i+1)
                .limit(4)
                .mapToObj(i -> Car.builder()
                        .id(i+1)
                        .regNumber(CAR__REG_NUMBERS[i])
                        .modelId(carModels.get(i%carModels.size()).getId())
                        .categoryId(categories.get(i%categories.size()).getId())
                        .locationId(locations.get(0).getId())
                        .build())
                .collect(Collectors.toList());
    }
    
    public static List<Person> createPersons() {
        List<Person> persons = new ArrayList<>();
        Person person = Person.builder()
                .id(1L)
                .phone("0123456789")
                .password("Admin#0")
                .name("John")
                .surname("Doe")
                .role(Role.ADMIN)
                .build();
        persons.add(person);
        return persons;
    }
}

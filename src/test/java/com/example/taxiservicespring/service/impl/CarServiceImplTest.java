package com.example.taxiservicespring.service.impl;

import static com.example.taxiservicespring.util.TestDataUtil.createCar;
import static com.example.taxiservicespring.util.TestDataUtil.createCarModel;
import static com.example.taxiservicespring.util.TestDataUtil.createCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.repository.CarModelRepository;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.service.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CarModelRepository carModelRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void findTest() {
        Car car = createCar();
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        CarDto carDto = carService.getById(anyLong());

        assertThat(carDto, allOf(
                hasProperty("id", equalTo(car.getId())),
                hasProperty("regNumber", equalTo(car.getRegNumber())),
                hasProperty("modelId",  equalTo(car.getModel().getId())),
                hasProperty("categoryId", equalTo(car.getCategory().getId())),
                hasProperty("locationId", equalTo(car.getLocation().getId())),
                hasProperty("status", equalTo(car.getStatus()))
                ));
    }

    @Test
    void findCarNotFoundTest() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> carService.getById(anyLong()));
    }

    @Test
    void findCategoryTest() {
        Category category = createCategory();
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        CategoryDto categoryDto = carService.getCategoryById(anyInt());

        assertThat(categoryDto, allOf(
                hasProperty("id", equalTo(category.getId())),
                hasProperty("name", equalTo(category.getName()))
                ));
    }

    @Test
    void findCategoryNotFoundTest() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> carService.getCategoryById(anyInt()));
    }

    @Test
    void findAllCategoriesTest() {
        Category category = createCategory();
        when(categoryRepository.findAllCategories()).thenReturn(List.of(category));

        List<CategoryDto> categoryDtoList = carService.getAllCategories();

        assertThat(categoryDtoList, hasSize(1));
        assertThat(categoryDtoList.get(0), allOf(
                hasProperty("id", equalTo(category.getId())),
                hasProperty("name", equalTo(category.getName()))
                ));
    }

    @Test
    void findCarModelTest() {
        CarModel carModel = createCarModel();
        when(carModelRepository.findById(anyLong())).thenReturn(Optional.of(carModel));

        CarModelDto carModelDto = carService.getCarModelById(anyLong());

        assertThat(carModelDto, allOf(
                hasProperty("id", equalTo(carModel.getId())),
                hasProperty("brand", equalTo(carModel.getBrand())),
                hasProperty("name", equalTo(carModel.getName())),
                hasProperty("year", equalTo(carModel.getYear())),
                hasProperty("color", equalTo(carModel.getColor()))
                ));
    }

    @Test
    void findCarModelNotFoundTest() {
        when(carModelRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> carService.getCarModelById(anyLong()));
    }
}
